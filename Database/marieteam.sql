-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 06 avr. 2023 à 21:48
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `marieteam`
--

-- --------------------------------------------------------

--
-- Structure de la table `bateau`
--

CREATE TABLE `bateau` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `longueur` float NOT NULL,
  `largeur` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `bateau`
--

INSERT INTO `bateau` (`id`, `nom`, `longueur`, `largeur`) VALUES
(1, 'Kor\' Ant', 64.8, 32.4),
(2, 'Ar Solen', 78.6, 35.8),
(3, 'Al\'Xi', 68.2, 34.7),
(4, 'Luce isle', 71, 35.1),
(5, 'Maellys', 64.2, 31.2);

-- --------------------------------------------------------

--
-- Structure de la table `bateauvoyageur`
--

CREATE TABLE `bateauvoyageur` (
  `id` int(11) NOT NULL,
  `id_bateau` int(11) NOT NULL,
  `vitesse` float NOT NULL,
  `img` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `bateauvoyageur`
--

INSERT INTO `bateauvoyageur` (`id`, `id_bateau`, `vitesse`, `img`) VALUES
(1, 1, 21.5, 'bateau1.jpg'),
(2, 2, 23.4, 'bateau2.jpg'),
(3, 3, 21.8, 'bateau3.jpg'),
(4, 4, 29.6, 'bateau4.jpg'),
(5, 5, 24, 'bateau5.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `bateauvoyageur_equipement`
--

CREATE TABLE `bateauvoyageur_equipement` (
  `id` int(11) NOT NULL,
  `id_bateauvoyageur` int(11) NOT NULL,
  `id_equipement` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `bateauvoyageur_equipement`
--

INSERT INTO `bateauvoyageur_equipement` (`id`, `id_bateauvoyageur`, `id_equipement`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 4),
(4, 2, 2),
(5, 2, 3),
(6, 2, 4),
(7, 3, 1),
(8, 3, 2),
(9, 3, 3),
(10, 3, 4),
(11, 4, 2),
(12, 4, 3),
(13, 5, 1),
(14, 5, 3),
(15, 5, 4);

-- --------------------------------------------------------

--
-- Structure de la table `capitaine`
--

CREATE TABLE `capitaine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `mail` varchar(50) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `capitaine`
--

INSERT INTO `capitaine` (`nom`, `prenom`, `mail`, `password`) VALUES
("Eau", "Pierre", "ep@ep.com", "$2y$10$S5NKL5AJnMND8CX6rjpCeeZzXdJS6H2itqz..THUtvPHzgO2T4MNa"),
("Passe", "Jean", "pj@pj.com", "$2y$10$v8uJB8PKzLw/glQmMfkpQOIDJ.Y20udqXDfDz8zOyHREJvII02WIW");

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

CREATE TABLE `equipement` (
  `id` int(11) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `equipement`
--

INSERT INTO `equipement` (`id`, `libelle`) VALUES
(1, 'Accès Handicapé'),
(2, 'Bar'),
(3, 'Pont Promenade'),
(4, 'Salon Vidéo');

-- --------------------------------------------------------

--
-- Structure de la table `traversee`
--

CREATE TABLE `traversee` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `heure_depart` time DEFAULT NULL,
  `date_heure_arrivee` datetime NOT NULL,
  `terminee` tinyint(1) NOT NULL,
  `etat_mer` varchar(255) DEFAULT NULL,
  `commentaire` varchar(255) DEFAULT NULL,
  `id_capitaine` int(11) NOT NULL,
  `id_bateauvoyageur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `traversee`
--

INSERT INTO `traversee` (`id`, `date`, `heure_depart`, `date_heure_arrivee`, `terminee`, `id_capitaine`,`id_bateauvoyageur`) VALUES
(1, '2023-03-28', '07:45:00', '2023-03-29 07:45:00', 1, 1, 1),
(2, '2023-03-28', '11:30:00', '2023-03-29 11:30:00', 1, 2, 4),
(3, '2023-03-29', '15:30:00', '2023-03-30 15:30:00', 1, 1, 3),
(4, '2023-03-30', '16:30:00', '2023-03-31 16:30:00', 1, 2, 1),
(5, '2023-03-29', '15:30:00', '2023-03-30 15:30:00', 0, 2, 5),
(6, '2023-04-28', '11:45:00', '2023-04-29 11:45:00', 0, 1, 4),
(7, '2023-05-28', '10:00:00', '2023-05-29 10:00:00', 0, 2, 2);

-- --------------------------------------------------------

--
-- Index pour la table `bateau`
--
ALTER TABLE `bateau`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `bateauvoyageur`
--
ALTER TABLE `bateauvoyageur`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bateauvoyageur_ibfk_1` (`id_bateau`);

--
-- Index pour la table `traversee`
--
ALTER TABLE `traversee`
  ADD KEY `id_capitaine_ibfk_1` (`id_capitaine`);

--
-- Index pour la table `bateauvoyageur_equipement`
--
ALTER TABLE `bateauvoyageur_equipement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bateauvoyageur_equipement_bateauvoyageur` (`id_bateauvoyageur`),
  ADD KEY `fk_bateauvoyageur_equipement_equipement` (`id_equipement`);

--
-- Index pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `traversee`
--
ALTER TABLE `traversee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idBateauvoyageur` (`id_bateauvoyageur`);

--
-- AUTO_INCREMENT pour la table `bateau`
--
ALTER TABLE `bateau`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `bateauvoyageur`
--
ALTER TABLE `bateauvoyageur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `bateauvoyageur_equipement`
--
ALTER TABLE `bateauvoyageur_equipement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `equipement`
--
ALTER TABLE `equipement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `traversee`
--
ALTER TABLE `traversee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Contraintes pour la table `bateauvoyageur`
--
ALTER TABLE `bateauvoyageur`
  ADD CONSTRAINT `bateauvoyageur_ibfk_1` FOREIGN KEY (`id_bateau`) REFERENCES `bateau` (`id`);

-- --------------------------------------------------------

--
-- Contraintes pour la table `bateauvoyageur_equipement`
--
ALTER TABLE `bateauvoyageur_equipement`
  ADD CONSTRAINT `fk_bateauvoyageur_equipement_bateauvoyageur` FOREIGN KEY (`id_bateauvoyageur`) REFERENCES `bateauvoyageur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_bateauvoyageur_equipement_equipement` FOREIGN KEY (`id_equipement`) REFERENCES `equipement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  
--
-- Contraintes pour la table `traversee`
--
ALTER TABLE `traversee`
  ADD CONSTRAINT `id_capitaine_ibfk_1` FOREIGN KEY (`id_capitaine`) REFERENCES `capitaine` (`id`),
  ADD CONSTRAINT `traversee_ibfk_2` FOREIGN KEY (`id_bateauvoyageur`) REFERENCES `bateauvoyageur` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
