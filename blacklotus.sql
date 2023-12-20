-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 11, 2022 at 11:27 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blacklotus`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_details`
--

CREATE TABLE `admin_details` (
  `Admin_ID` int(225) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Contact_Number` int(10) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin_details`
--

INSERT INTO `admin_details` (`Admin_ID`, `Name`, `Email`, `Contact_Number`, `Password`) VALUES
(1, 'Admin', 'admin@gmail.com', 712345688, 'java1234');

-- --------------------------------------------------------

--
-- Table structure for table `employee_details`
--

CREATE TABLE `employee_details` (
  `Employee_ID` int(225) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Contact_Number` int(11) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee_details`
--

INSERT INTO `employee_details` (`Employee_ID`, `Name`, `Email`, `Contact_Number`, `Gender`, `Password`) VALUES
(1, 'John', 'cena@gmail.com', 123456789, 'Male', 'java1234'),
(2, 'Nikki', 'nikki@gmail.com', 753951654, 'Female', 'java1234'),
(3, 'Ronaldo', 'cr7@gmail.com', 654987321, 'Male', 'java1234'),
(4, 'Bruno', 'bruno@gmail.com', 852654951, 'Male', 'java1234');

-- --------------------------------------------------------

--
-- Table structure for table `sales_details`
--

CREATE TABLE `sales_details` (
  `Sale_ID` int(255) NOT NULL,
  `Total` int(255) NOT NULL,
  `Profit` int(255) NOT NULL,
  `Sold_Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sales_details`
--

INSERT INTO `sales_details` (`Sale_ID`, `Total`, `Profit`, `Sold_Date`) VALUES
(1, 500, 100, '2022-02-01'),
(2, 1500, 200, '2022-01-12'),
(3, 1350, 125, '2022-02-05'),
(4, 550, 50, '2022-02-09'),
(5, 550, 50, '2022-02-09'),
(6, 250, 25, '2022-02-11'),
(7, 1100, 100, '2022-02-11');

-- --------------------------------------------------------

--
-- Table structure for table `stock_details`
--

CREATE TABLE `stock_details` (
  `Item_ID` int(225) NOT NULL,
  `Item_Name` varchar(30) NOT NULL,
  `Category` varchar(30) NOT NULL,
  `Quantity` int(225) NOT NULL,
  `Price` int(30) NOT NULL,
  `Profit` int(30) NOT NULL,
  `Added_Date` date NOT NULL,
  `Supplier_ID` int(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stock_details`
--

INSERT INTO `stock_details` (`Item_ID`, `Item_Name`, `Category`, `Quantity`, `Price`, `Profit`, `Added_Date`, `Supplier_ID`) VALUES
(1, 'Ice coffee', 'Beverages', 3, 110, 10, '2022-02-01', 5),
(2, 'Gold Marie', 'Dry/Baking', 25, 50, 5, '2022-02-02', 2),
(3, 'Toblerone', 'Dry/Baking', 30, 1500, 200, '2022-02-04', 5),
(4, 'Cream Cracker', 'Beverages', 45, 110, 20, '2022-02-09', 1),
(5, 'ibdsivbisb', 'Beverages', 150, 500, 50, '2022-02-11', 1);

-- --------------------------------------------------------

--
-- Table structure for table `supplier_details`
--

CREATE TABLE `supplier_details` (
  `Supplier_ID` int(225) NOT NULL,
  `Supplier_Name` varchar(30) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Contact_Number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier_details`
--

INSERT INTO `supplier_details` (`Supplier_ID`, `Supplier_Name`, `Email`, `Contact_Number`) VALUES
(1, 'Munchee', 'cbl@gmail.com', 1234567895),
(2, 'Maliban', 'maliban@gmail.com', 987654321),
(3, 'Anchor', 'anchor@gmail.com', 112345825),
(4, 'Baby Cheramy', 'babycheramy@gmail.com', 741852963),
(5, 'Nestle', 'nestle@gmail.com', 951357852),
(6, 'Perera & Sons', 'pns@gmail.com', 789951357),
(7, 'Pussalla', 'pussalla@gmail.com', 753951654),
(8, 'Kraft', 'kraft@gmail.com', 654591234),
(9, 'Lysol', 'lysol2gmail.com', 769539451),
(10, 'Nike', 'nike@gmail.com', 753651249),
(11, 'Cadbury', 'cadbury@gmail.com', 754124692);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_details`
--
ALTER TABLE `admin_details`
  ADD PRIMARY KEY (`Admin_ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `employee_details`
--
ALTER TABLE `employee_details`
  ADD PRIMARY KEY (`Employee_ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `sales_details`
--
ALTER TABLE `sales_details`
  ADD PRIMARY KEY (`Sale_ID`);

--
-- Indexes for table `stock_details`
--
ALTER TABLE `stock_details`
  ADD PRIMARY KEY (`Item_ID`),
  ADD KEY `Supplier_ID` (`Supplier_ID`);

--
-- Indexes for table `supplier_details`
--
ALTER TABLE `supplier_details`
  ADD PRIMARY KEY (`Supplier_ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_details`
--
ALTER TABLE `admin_details`
  MODIFY `Admin_ID` int(225) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `employee_details`
--
ALTER TABLE `employee_details`
  MODIFY `Employee_ID` int(225) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sales_details`
--
ALTER TABLE `sales_details`
  MODIFY `Sale_ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `stock_details`
--
ALTER TABLE `stock_details`
  MODIFY `Item_ID` int(225) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `supplier_details`
--
ALTER TABLE `supplier_details`
  MODIFY `Supplier_ID` int(225) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `stock_details`
--
ALTER TABLE `stock_details`
  ADD CONSTRAINT `stock_details_ibfk_1` FOREIGN KEY (`Supplier_ID`) REFERENCES `supplier_details` (`Supplier_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
