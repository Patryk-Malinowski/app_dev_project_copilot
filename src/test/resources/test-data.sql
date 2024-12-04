INSERT INTO household (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) VALUES
('D04 X4K2', 2, 4, true),
('D06 H9P3', 3, 5, false),
('D08 F7M1', 1, 2, true);

INSERT INTO pets (name, animal_type, breed, age, household_eircode) VALUES
('Max', 'Dog', 'Labrador', 3, 'D04 X4K2'),
('Luna', 'Cat', 'Persian', 2, 'D04 X4K2'),
('Charlie', 'Dog', 'Beagle', 5, 'D06 H9P3'),
('Lucy', 'Cat', 'Siamese', 4, 'D08 F7M1');