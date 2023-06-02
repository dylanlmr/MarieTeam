<?php

include_once '../../includes/db_connect.php';
include_once '../../includes/response.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Récupération du corps de la requête POST
    $requestBody = file_get_contents('php://input');

    // Décode le corps de la requête JSON en objet PHP
    $requestData = json_decode($requestBody);

    // Vérification des données POST
    if (!$requestData || !isset($requestData->mail) || !isset($requestData->password) || empty($requestData->mail) || empty($requestData->password)) {
        response(false, 'Tous les champs doivent être remplis');
    } else {
        // Récupération du nom d'utilisateur et du mot de passe à partir des données POST
        $mail = $requestData->mail;
        $password = $requestData->password;

        // Récupération du mot de passe hashé dans la base de données
        $sql = "SELECT * FROM capitaine WHERE mail = :mail";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':mail', $mail);
        $stmt->execute();
        $capitaine = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($capitaine) {
            // Vérification du mot de passe
            if (password_verify($password, $capitaine['password'])) {
                response(true, 'Capitaine trouvé', $capitaine);
            } else {
                response(false, 'Mot de passe incorrect');
            }
        } else {
            response(false, 'Adresse mail incorrecte');
        }
    }
}

?>