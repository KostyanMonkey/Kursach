abstract sig Asset { }

abstract sig Participant { 
	uses: set Asset,
	allowed: set Asset,
	denied: set Asset,
}

abstract sig Transaction {
	includes: one Asset,
	from: one Participant,
	to: one Participant,
}

fact {
	all t: Transaction | t.from.allowed & t.includes != none
}

one sig customer, service_specialist extends Participant { }

assert Structure {
	all o: Transaction | 
           (one a: Asset | a in o.includes) and 
           (one p: Participant | p in o.to or p in o.from)}
run {
    all a: Asset |
	a in service_specialist.uses => a not in service_specialist.denied
} for 4 but exactly 2 Participant, 3 Asset
check Structure
