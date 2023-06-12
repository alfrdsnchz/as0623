DROP TABLE IF EXISTS tools; 

DROP TABLE IF EXISTS tool_rates; 

CREATE TABLE IF NOT EXISTS tools (
    tool_code varchar(8) not null,
    tool_type varchar(32) not null,
    tool_brand varchar(32) not null,
    PRIMARY KEY(tool_code)
);

CREATE TABLE IF NOT EXISTS tool_rates (
    tool_type varchar(32) not null,
    daily_rate integer not null,
    weekday_charge boolean not null,
    weekend_charge boolean not null,
    holiday_charge boolean not null,
    PRIMARY KEY(tool_type)
);

INSERT INTO tools (tool_code, tool_type, tool_brand)
values 
('CHNS', 'Chainsaw', 'Stihl'),
('LADW', 'Ladder', 'Werner'),
('JAKD', 'Jackhammer', 'DeWalt'),
('JAKR', 'Jackhammer', 'Ridgid');

INSERT INTO tool_rates (tool_type, daily_rate, weekday_charge, weekend_charge, holiday_charge)
values 
('Ladder', 199, true, true, false),
('Chainsaw', 149, true, false, true),
('Jackhammer', 299, true, false, false);