import csv
import zipfile
import os

# Define the templates for CTO and ACL files
cto_template = """
asset {name} identified by {id} {{
  o String {id};
  o String {type};
  {properties}
}}"""

acl_template = """
rule AllowAllReadAccessFor{name} {{
  description: "Allow all participants read access to {name} assets"
  participant: "org.example.{name}Participant"
  operation: READ
  resource: "org.example.{name}.{id}"
  action: ALLOW
}}"""

# Function to prompt the user for a file name
def get_filename(prompt):
    while True:
        filename = input(prompt)
        # Check if the file has a .csv extension
        if not filename.lower().endswith('.csv'):
            print("Please provide a file with a .csv extension.")
        # Check if the file exists
        elif not os.path.isfile(filename):
            print("File not found. Please provide an existing file.")
        else:
            return filename


# Function to create asset properties from a CSV row
def create_properties(row):
    properties = ""
    # Iterate over each item in the row
    for key, value in row.items():
        # Exclude 'ID', 'Type', 'Name' and empty values
        if key not in ['ID', 'Type', 'Name'] and value:
            properties += f'  o String {key};\n'
    return properties.strip()


# Function to process CSV and generate CTO and ACL content
def process_csv(csv_file):
    with open(csv_file, newline='') as file:
        reader = csv.DictReader(file)
        cto_content, acl_content = "", ""

        # Process each row in the CSV
        for row in reader:
            id, type, name = row['ID'], row['Type'], row['Name'].replace(' ', '')
            properties = create_properties(row)
            # Format the CTO and ACL content using templates and properties
            cto_content += cto_template.format(id=id, type=type, name=name, properties=properties)
            acl_content += acl_template.format(id=id, name=name)

        return cto_content, acl_content


# Function to write content to a file
def write_to_file(filename, content):
    with open(filename, 'w') as file:
        file.write(content)


# Function to create a BNA file
def create_bna(cto_file, acl_file, bna_file):
    with zipfile.ZipFile(bna_file, 'w') as myzip:
        # Add CTO and ACL files to the BNA zip archive
        myzip.write(cto_file)
        myzip.write(acl_file)


# Main execution of the program

csv_filename = get_filename("Enter the CSV file name: ")
bna_filename = input("Enter the desired BNA file name (without extension): ")

# Ensure the BNA file name has the correct extension
bna_filename = f"{bna_filename}.bna"
cto_filename = f"{bna_filename.replace('.bna', '')}.cto"
acl_filename = f"{bna_filename.replace('.bna', '')}.acl"

cto_content, acl_content = process_csv(csv_filename)

# Write the CTO and ACL content to files
write_to_file(cto_filename, cto_content)
write_to_file(acl_filename, acl_content)
# Create the BNA file with matching CTO and ACL file names
create_bna(cto_filename, acl_filename, bna_filename)
print(f'BNA file {bna_filename} created successfully.')