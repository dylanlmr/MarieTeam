<?php

include_once '../../includes/db_connect.php';
include_once '../../includes/response.php';

if($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Récupérer l'ID du capitaine à partir de l'URI
    $id = $_GET['id'];

    // Récupérer les données de la traversée
    $sql = "SELECT id, date, heure_depart, date_heure_arrivee, terminee, etat_mer, commentaire, id_capitaine FROM traversee WHERE id_capitaine = :id";
    $stmt = $conn->prepare($sql);
    $stmt->bindParam(':id', $id);
    $stmt->execute();
    $traversees = $stmt->fetchAll(PDO::FETCH_ASSOC);

    if($traversees) {
        response(true, 'Traversée(s) trouvée(s)', $traversees);
    } else {
        response(false, 'Traversée(s) non trouvée(s)');
    }
} else {
    response(false, 'Requête invalide');
}

?>