<?php

include_once '../../includes/db_connect.php';
include_once '../../includes/response.php';

$data = json_decode(file_get_contents('php://input'));

try {
    $conn->beginTransaction();
    
    foreach($data as $item) {
        
        if(!isset($item->id) || !isset($item->terminee) || !isset($item->etat_mer) || !isset($item->commentaire)) {
            response(false, "Données manquantes pour un élément du tableau");
        }

        updateTraversee($conn, $item->id, $item->terminee, $item->etat_mer, $item->commentaire);
    }

    $conn->commit();
    response(true, "Traversées mises à jour");

} catch(Exception $e) {
    $conn->rollback();
    
    response(false, "Erreur lors de la mise à jour de la traversée : " . $e->getMessage());
}

function updateTraversee($conn, $id, $terminee, $etat_mer, $commentaire) {

    $stmt = $conn->prepare("UPDATE traversee SET terminee = :terminee, etat_mer = :etat_mer, commentaire = :commentaire WHERE id = :id");
    $stmt->bindParam(':terminee', $terminee);
    $stmt->bindParam(':etat_mer', $etat_mer);
    $stmt->bindParam(':commentaire', $commentaire);
    $stmt->bindParam(':id', $id);
    
    $stmt->execute();
}

?>