insert into drones values(1,1234567,'Lightweight', 450,100, 'IDLE');
insert into drones values(2,1234568,'Cruiserweight', 310,100, 'IDLE');
insert into drones values(3,1234569,'Heavyweight', 500,100, 'IDLE');
insert into drones values(4,1234563,'Lightweight', 120,100, 'IDLE');

insert into medications values(1,'Acetaminophen','ACET', 13, RAWTOHEX('Acetaminophen_image'));
insert into medications values(2,'Paracetamol','PARAC', 185, RAWTOHEX('Paracetamol_image'));
insert into medications values(3,'Flagyl','FLAG', 57, RAWTOHEX('Flagyl_image'));
insert into medications values(4,'Promethazine','PROM', 13, RAWTOHEX('Promethazine_image'));

insert into dispatches values(1,1,'INITIATED');

insert into dispatch_items values(1,1,1,1);
insert into dispatch_items values(2,1,2,1);
insert into dispatch_items values(3,1,3,1);
