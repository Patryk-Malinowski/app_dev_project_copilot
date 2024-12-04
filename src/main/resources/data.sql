-- Insert households
INSERT INTO household (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) VALUES
('D01AB12', 3, 4, true),
('D02CD34', 2, 3, false),
('D03EF56', 4, 5, true),
('D04GH78', 1, 2, true),
('D05IJ90', 2, 4, false);

-- Insert pets
INSERT INTO pets (name, animal_type, breed, age, household_eircode) VALUES
('Luna', 'Cat', 'Persian', 3, 'D01AB12'),
('Max', 'Dog', 'Golden Retriever', 5, 'D01AB12'),
('Bella', 'Cat', 'Siamese', 2, 'D02CD34'),
('Charlie', 'Dog', 'Labrador', 4, 'D03EF56'),
('Lucy', 'Dog', 'Beagle', 1, 'D03EF56'),
('Oscar', 'Cat', 'Maine Coon', 6, 'D03EF56'),
('Daisy', 'Dog', 'Poodle', 3, 'D04GH78'),
('Milo', 'Cat', 'Ragdoll', 4, 'D05IJ90'),
('Rocky', 'Dog', 'German Shepherd', 2, 'D05IJ90'),
('Coco', 'Cat', 'British Shorthair', 5, 'D01AB12');