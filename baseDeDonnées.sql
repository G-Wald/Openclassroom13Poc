CREATE TABLE Users (
     Id INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(100) UNIQUE,
    Username VARCHAR(100) UNIQUE,
    Password VARCHAR(255),
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DateOfBirth DATE,
    Address VARCHAR(255)
);

CREATE TABLE RentalAgencies (
    AgencyID INT PRIMARY KEY AUTO_INCREMENT,
    AgencyName VARCHAR(100),
    Location VARCHAR(255),
    PhoneNumber VARCHAR(20)
);

CREATE TABLE RentalOffers (
    OfferID INT PRIMARY KEY AUTO_INCREMENT,
    AgencyID INT,
    PickupLocation VARCHAR(255),
    ReturnLocation VARCHAR(255),
    PickupDateTime DATETIME,
    ReturnDateTime DATETIME,
    VehicleCategory VARCHAR(50),
    Rate DECIMAL(10, 2),
    FOREIGN KEY (AgencyID) REFERENCES RentalAgencies(AgencyID)
);

CREATE TABLE ChatMessages (
    MessageID INT PRIMARY KEY AUTO_INCREMENT,
    SenderID INT,
    ReceiverID INT,
    MessageText TEXT,
    Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (SenderID) REFERENCES Users(Id),
    FOREIGN KEY (ReceiverID) REFERENCES Users(Id)
);

CREATE TABLE Reservations (
    ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    RentalOfferID INT,
    PickupLocation VARCHAR(255),
    ReturnLocation VARCHAR(255),
    PickupDateTime DATETIME,
    ReturnDateTime DATETIME,
    ModificationDeadline DATETIME,
    VehicleCategory VARCHAR(50),
    TotalAmount DECIMAL(10, 2),
    Status VARCHAR(20),
    FOREIGN KEY (UserID) REFERENCES Users(Id),
    FOREIGN KEY (RentalOfferID) REFERENCES RentalOffers(OfferID)
);

CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    ReservationID INT,
    Amount DECIMAL(10, 2),
    PaymentDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(20),
    FOREIGN KEY (ReservationID) REFERENCES Reservations(ReservationID)
);


INSERT INTO Users (Email, Username, Password) VALUES 
('tata@gmail.com', 'tata', 'tata'),
('sav@gmail.com', 'sav', 'sav');