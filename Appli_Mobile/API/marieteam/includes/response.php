<?php

$response = array();

function response($success, $message, $data = NULL) {
    $response['success'] = $success;
    $response['message'] = $message;
    if($data) {
        $response['data'] = $data;
    }
    header("content-type: application/json; charset=utf-8");
    echo json_encode($response);
    exit;
}

?>