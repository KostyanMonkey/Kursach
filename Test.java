package com.example.zov;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;

import java.io.File;
import java.util.*;

public class Test {

    public static boolean bpmnValid = false;
    public static void Check(String path)
    {
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(new File(path));
        boolean isConsistent = checkConsistency(modelInstance);

        if (isConsistent) {
            bpmnValid = true;
        } else {
            bpmnValid = false;
        }
    }

    private static boolean checkConsistency(BpmnModelInstance modelInstance) {
        Collection<StartEvent> startEvents = modelInstance.getModelElementsByType(StartEvent.class);
        Collection<EndEvent> endEvents = modelInstance.getModelElementsByType(EndEvent.class);
        Collection<Task> tasks = modelInstance.getModelElementsByType(Task.class);
        Collection<SequenceFlow> sequenceFlows = modelInstance.getModelElementsByType(SequenceFlow.class);

        if (startEvents.isEmpty()) {
            System.out.println("The process has no start event.");
            return false;
        }
        if (endEvents.isEmpty()) {
            System.out.println("The process has no end event.");
            return false;
        }

        for (Task task : tasks) {
            boolean hasIncoming = sequenceFlows.stream().anyMatch(flow -> flow.getTarget().equals(task));
            boolean hasOutgoing = sequenceFlows.stream().anyMatch(flow -> flow.getSource().equals(task));
            if (!hasIncoming || !hasOutgoing) {
                System.out.println("The task " + task.getName() + " is isolated.");
                return false;
            }
        }

        // Check for cycles or deadlocks
        if (hasCycle(modelInstance)) {
            System.out.println("The process has a cycle.");
            return false;
        }

        return true;
    }

    private static boolean hasCycle(BpmnModelInstance modelInstance) {
        Collection<FlowNode> nodes = modelInstance.getModelElementsByType(FlowNode.class);
        Map<FlowNode, Boolean> visited = new HashMap<>();
        Map<FlowNode, Boolean> stack = new HashMap<>();

        for (FlowNode node : nodes) {
            visited.put(node, false);
            stack.put(node, false);
        }

        for (FlowNode node : nodes) {
            if (detectCycle(node, visited, stack)) {
                return true;
            }
        }

        return false;
    }

    private static boolean detectCycle(FlowNode node, Map<FlowNode, Boolean> visited, Map<FlowNode, Boolean> stack) {
        if (stack.get(node)) {
            return true;
        }
        if (visited.get(node)) {
            return false;
        }

        visited.put(node, true);
        stack.put(node, true);

        for (SequenceFlow flow : node.getOutgoing()) {
            if (detectCycle(flow.getTarget(), visited, stack)) {
                return true;
            }
        }

        stack.put(node, false);
        return false;
    }
}