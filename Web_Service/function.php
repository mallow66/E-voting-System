<?php
/**
 * Created by PhpStorm.
 * User: brahim
 * Date: 30/12/16
 * Time: 22:14
 */

include 'connection.php';

function personneExcelExist($cin){
    global $bdd; 
    $requete = $bdd->prepare("SELECT * from personne where cin = ?"); 
    $requete->bindParam(1,$cin ); 
    $requete->execute(); 
    if($requete->rowCount()>0)
        return true; 
    return false; 
}

function modifierPersonneExcel($cin, $nom, $prenom){
    global $bdd;
    $requete = $bdd->prepare("UPDATE personne SET nom = ? , prenom = ? where cin = ?");
    $requete->bindParam(1,$nom );
    $requete->bindParam(2,$prenom );
    $requete->bindParam(3,$cin );
    if($requete->execute()) return true;
    return false; 
}
function modifierPersonneExcel2($cin, $nom, $prenom, $email){
    global $bdd;
    $requete = $bdd->prepare("UPDATE personne SET nom = ? , prenom = ?, email = ? where cin = ?");
    $requete->bindParam(1,$nom );
    $requete->bindParam(2,$prenom );
    $requete->bindParam(3,$email );
    $requete->bindParam(4,$cin );
    if($requete->execute()) return true;
    return false;
}

function ajouterPersonneExcel($cin,$nom, $prenom){
    global $bdd;
    $requete = $bdd->prepare('INSERT INTO personne(cin, nom, prenom) values(?,?,?)');
    $requete->bindParam(1,$cin);
    $requete->bindParam(2,$nom);
    $requete->bindParam(3,$prenom);

    if($requete->execute())
        return true;
    else
        return false;
}

function ajouterPersonneExcel2($cin,$nom, $prenom,$email){
    global $bdd;
    $requete = $bdd->prepare('INSERT INTO personne(cin, nom, prenom, email) values(?,?,?,?)');
    $requete->bindParam(1,$cin);
    $requete->bindParam(2,$nom);
    $requete->bindParam(3,$prenom);
    $requete->bindParam(4,$email );

    if($requete->execute())
        return true;
    else
        return false;
}

function ajouterElecteurExcel($cin,$nom, $prenom){
    global $bdd;
    if(personneExcelExist($cin)) modifierPersonneExcel($cin, $nom, $prenom);
    else ajouterPersonneExcel($cin, $nom, $prenom);
    $requete = $bdd->prepare("INSERT INTO electeur values(?)"); 
    $requete->bindParam(1,$cin );
    if($requete->execute()) return true; 
    else return false;
    
}

function ajouterElecteurExcel2($cin,$nom, $prenom, $email){
    global $bdd;
    if(personneExcelExist($cin)) modifierPersonneExcel2($cin, $nom, $prenom, $email);
    else ajouterPersonneExcel2($cin, $nom, $prenom, $email);
    $requete = $bdd->prepare("INSERT INTO electeur values(?)");
    $requete->bindParam(1,$cin );
    if($requete->execute()) return true;
    else return false;

}

function ajouterElecteurAbonnementExcel($cin,$election){
    global $bdd;
    $requete = $bdd->prepare("INSERT INTO abonnement(electeur, election) values(?, ?)");
    $requete->bindParam(1,$cin );
    $requete->bindParam(2,$election );
    if($requete->execute()) return true;
    else return false;
}


function importerFichierExcel($file){
    $tmpfname = basename($file);
    $excelReader = PHPExcel_IOFactory::createReaderForFile($tmpfname);
    $excelObj = $excelReader->load($tmpfname);
    $worksheet = $excelObj->getSheet(0);
    $lastRow = $worksheet->getHighestRow();

    for($row=2; $row<=$lastRow; $row ++) {
        $cin = $worksheet->getCell('A'.$row)->getValue();
        $nom = $worksheet->getCell('B'.$row)->getValue();
        $prenom = $worksheet->getCell('C'.$row)->getValue();
        $election = $worksheet->getCell('D'.$row)->getValue();

        echo "election : ".$election;

         $b = ajouterElecteurExcel($cin, $nom, $prenom);
         $b2 = ajouterElecteurAbonnementExcel($cin, $election);
        
        if($b && $b2) echo "succes deux<br>"; 
        
        
        
    }

   
}

function importerFichierExcel2($file){
    $tmpfname = basename($file);
    $excelReader = PHPExcel_IOFactory::createReaderForFile($tmpfname);
    $excelObj = $excelReader->load($tmpfname);
    $worksheet = $excelObj->getSheet(0);
    $lastRow = $worksheet->getHighestRow();

    for($row=2; $row<=$lastRow; $row ++) {
        $cin = $worksheet->getCell('A'.$row)->getValue();
        $nom = $worksheet->getCell('B'.$row)->getValue();
        $prenom = $worksheet->getCell('C'.$row)->getValue();
        $email =  $worksheet->getCell('D'.$row)->getValue();
        $election = $worksheet->getCell('E'.$row)->getValue();

        echo "election : ".$election;

        $b = ajouterElecteurExcel2($cin, $nom, $prenom, $email);
        $b2 = ajouterElecteurAbonnementExcel($cin, $election);
        insertCode($cin); 

        if($b && $b2) echo "succes deux<br>";



    }


}

function electeurExist($cin){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from electeur where id_electeur = ?");
    $requete->bindParam(1,$cin );
    if($requete->rowCount()>0)
        return true;
    return false;
}

function personneExist($cin){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from personne where cin= ?");
    $requete->bindParam(1,$cin );
    $requete->execute();
    if($requete->rowCount()>0)
        return true;
    return false;
}

function personneEstActif($cin){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from personne where cin = ?");
    $requete->bindParam(1,$cin );
    $requete->execute();
    if($requete->rowCount() == 0)
        return -1;
    $ligne = $requete->fetch();
    return $ligne['actif'];
}

function getPersonneNonActif($cin){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from personne where cin = ? ");
    $requete->bindParam(1,$cin);

    $requete->execute();
    $ligne = $requete->fetch();
    return $ligne;
}

function ajouterPersonne($cin, $nom, $prenom, $email, $password){
    global $bdd;
    $bool = true;
    $requete = $bdd->prepare("INSERT INTO personne values(?,?,?,?,?,?)");
    $requete->bindParam(1,$cin );
    $requete->bindParam(2,$nom );
    $requete->bindParam(3,$prenom );
    $requete->bindParam(4,$email );
    $requete->bindParam(5,$password );
    $requete->bindParam(6,$bool);
    if($requete->execute())
        return true; 
    else return false; 
}

function ajouterElecteur($cin, $nom, $prenom, $email, $password){
    global $bdd; 
    $b1 = ajouterPersonne($cin, $nom, $prenom, $email, $password); 
    $requete = $bdd->prepare("INSERT INTO electeur values(?)"); 
    $requete->bindParam(1,$cin);
    $b2 = $requete->execute();
    return $b1 && $b2;
}

function ajouterPersonneExist($cin, $email, $password){
    global $bdd; 
    $bool = true; 
    $requete = $bdd->prepare("UPDATE personne SET email=?, password=?, actif=?  where cin=? ");
    $requete->bindParam(1,$email );
    $requete->bindParam(2,$password ); 
    $requete->bindParam(3,$bool ); 
    $requete->bindParam(4, $cin); 
    if($requete->execute()) return true; 
    else return false;
    
}

function ajouterPersonneExist2($cin, $password){
    global $bdd;
    $bool = true;
    $requete = $bdd->prepare("UPDATE personne SET  password=?, actif=?  where cin=? ");
    $requete->bindParam(1,$password );
    $requete->bindParam(2,$bool );
    $requete->bindParam(3, $cin);
    if($requete->execute()) return true;
    else return false;

}

function getElecteur($cin){
    global $bdd;
    if(electeurExist($cin)){
        $requete = $bdd->prepare("SELECT * from personne where cin = ? ");
        $requete->bindParam(1,$cin);

        $requete->execute();
        $ligne = $requete->fetch();
        return $ligne;
    }
    else return -1; 
    
}

function electeurExistDansAbonnement($cin, $codeElection){
    global $bdd; 
    $requete = $bdd->prepare("SELECT * from abonnement where electeur = ? and election = ? "); 
    $requete->bindParam(1, $cin); 
    $requete->bindParam(2, $codeElection); 
    $requete->execute(); 
    if($requete->rowCount()>0)
        return true;
    else return false; 
}

function electionExist($codeElection){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from election where id_election= ?");
    $requete->bindParam(1,$codeElection);
    $requete->execute();
    if($requete->rowCount()>0)
        return true;
    return false;
}

function abonnerelecteurElection($cin, $codeElection){
    global $bdd; 
    if(electeurExistDansAbonnement($cin, $codeElection)){
        $bool = true; 
        $requete = $bdd->prepare("UPDATE abonnement set abonne = ? where electeur = ? and election = ?  "); 
        $requete->bindParam(1,$bool ); 
        $requete->bindParam(2,$cin ); 
        $requete->bindParam(3,$codeElection );
        if($requete->execute()) return true; 
        else return false; 
    }
    return false; 
}


function electeurDejaAbonne($cin, $codeElection){
    global $bdd; 
    $bool = true; 
    $requete = $bdd->prepare("SELECT * from abonnement where electeur = ? and election = ? and abonne = ?"); 
    $requete->bindParam(1,$cin ); 
    $requete->bindParam(2,$codeElection );
    $requete->bindParam(3, $bool); 
    $requete->execute(); 
    if($requete->rowCount()>0) return true; 
    else return false; 
    
}


function loginPersonne($email, $password){
    global $bdd; 
    $requete = $bdd->prepare("SELECT * from personne where email = ? and password = ?"); 
    $requete->bindParam(1,$email ); 
    $requete->bindParam(2,$password ); 
    $requete->execute(); 
    if($requete->rowCount()>0) return $requete->fetch(); 
    return -1; 
    
}

function getTypePersonne($cin){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from electeur where id_electeur= ? ");
    $requete->bindParam(1,$cin );
    $requete->execute();
    if($requete->rowCount()>0) return "electeur";


    $requete = $bdd->prepare("SELECT * from candidat where id_candidat= ? ");
    $requete->bindParam(1,$cin );
    $requete->execute();
    if($requete->rowCount()>0) return "candidat";
    
    return -1; 
}


function getAbonnementListView($cin){
    global $bdd;

    $requete = $bdd->prepare("SELECT * from personne as e, abonnement as a, election as el 
                                where e.cin = a.electeur
                                and a.election = el.id_election 
                                and e.cin = ?
                                and a.abonne = true");

    $requete->bindParam(1,$cin );
    $requete->execute(); 
    $result= array(); 
    $requete->execute(); 
    while($ligne = $requete->fetch(PDO::FETCH_ASSOC)){
        array_push($result, array('election'=>$ligne['election'], "nom_election"=>$ligne['nom_election'],"abonne"=>$ligne['abonne'],"a_vote"=>$ligne['a_vote'],  "date_debut"=>$ligne['date_debut'], "date_fin"=>$ligne['date_fin']));
    }
    return $result;

}


function getVotesElection($codeElection){
    global $bdd; 
    $requete = $bdd->prepare("SELECT p.nom ,p.prenom,v.candidat from personne as p, vote as v 
                                where p.cin = v.candidat and v.election = ? ");
    $requete->bindParam(1,$codeElection ); 
    $requete->execute(); 
    return $requete->fetchAll(); 
}

function setAVoteElecteur($electeur, $election){
    global $bdd;
    $bool = true;
    $requete = $bdd->prepare("UPDATE abonnement SET a_vote = ? where electeur = ? and election = ?");
    $requete->bindParam(1,$bool );
    $requete->bindParam(2, $electeur);
    $requete->bindParam(3, $election); 
    if($requete->execute()) return true;
    return false;
}

//les votes 


function getNbreVote($candidat, $election){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from vote where candidat = ? and election = ? ");
    $requete->bindParam(1,$candidat );
    $requete->bindParam(2,$election );
    $requete->execute();
    $ligne = $requete->fetch();
    return $ligne['nbre_vote'];
}

function incrementVoteCanVoteCandidat($candidat, $election){
    global $bdd;
    $n = getNbreVote($candidat, $election); 
    $n = $n+1; 
    $requete = $bdd->prepare("UPDATE vote SET nbre_vote = ? where candidat = ? and election = ?"); 
    $requete->bindParam(1,$n ); 
    $requete->bindParam(2,$candidat ); 
    $requete->bindParam(3, $election); 
    if($requete->execute()) return true; 
    return false; 
}


function getCurrentDate(){
    global $bdd;
    $requete = $bdd->prepare("SELECT NOW()  AS CurrentDateTime ");
    $requete->execute(); 
    $ligne = $requete->fetch(); 
    return $ligne['CurrentDateTime']; 
}

function electionisOnResult($codeElection){
    global $bdd; 
    $requete = $bdd->prepare("SELECT * from resultat where election  = ? "); 
    $requete->bindParam(1, $codeElection); 
    $requete->execute(); 
    if($requete->rowCount()>0) return true; 
    return false; 
}

function getElectionResult($codeElection){
    global $bdd; 
    $requete = $bdd->prepare("SELECT p.nom, p.prenom, v.nbre_vote from personne as p, vote as v
                                where p.cin = v.candidat and election = ? ORDER BY nbre_vote DESC ");
    $requete->bindParam(1, $codeElection);
    $requete->execute() ;
    return $requete->fetchAll(); 
}


function insertUserFirbase($token, $cin){
    global $bdd; 
    $requete = $bdd->prepare('INSERT INTO firebase_users values(?, ?)');
    $requete->bindParam(1,$token ) ; 
    $requete->bindParam(2, $cin) ; 
    if($requete->execute()) return true; 
    return false; 
}
function insertUserFirbase2($token){
    global $bdd;
    $requete = $bdd->prepare('INSERT INTO firebase_users(token) values(?)');
    $requete->bindParam(1,$token ) ;
    if($requete->execute()) return true;
    return false;
}

function sendNotification($tokens, $message){
    $url = 'https://fcm.googleapis.com/fcm/send';
    $fields = array(
        'registration_ids' => $tokens,
        'data' => $message
    );

    $headers = array(
        'Authorization:key = AAAAeB9WcIA:APA91bGzDyyywH_mmzYtN0M3bvcwsGtbPjvE9_27S8sxn6lNW6lXm5HmIoRYM-A8vIklJTr_jZKQA4hDttF8QVQ9cxi-6hndxXP1rETdnyVanuHAPgZn0KAI07nCl36r72r3Ith9Ne9g',
        'Content-Type: application/json'
    );

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    $result = curl_exec($ch);
    if ($result === FALSE) {
        die('Curl failed: ' . curl_error($ch));
    }
    curl_close($ch);
    return $result;

}

function getTokensForElectionResult($election){
    global $bdd;
    $tokens = array();

    $requete = $bdd->prepare('SELECT f.token from firebase_users as f, abonnement as a 
                                where f.cin = a.electeur and a.election = ?');
    $requete->bindParam(1,$election ) ;
    $requete->execute();
    if($requete->rowCount()>0){
        while($ligne = $requete->fetch()){
            $tokens[] = $ligne['token'];

        }
        return $tokens;

    }
    return -1;
}

function insertResultat($election){
    global $bdd;
    $requete = $bdd->prepare('INSERT INTO resultat values(?)');
    $requete->bindParam(1, $election);
    if($requete->execute())
        return true;
    return false;
}

function getNomElection($election){
    global $bdd;
    $requete = $bdd->prepare('SELECT nom_election from election where id_election = ?');
    $requete->bindParam(1, $election); 
    $requete->execute();
    $ligne = $requete->fetch();
    return $ligne['nom_election'] ;
}


function getCodesElections($cin){
    global $bdd; 
    $requete = $bdd->prepare("SELECT  a.election, e.nom_election from  abonnement as a, election as e 
                              where a.election = e.id_election and a.electeur = ? ");
    $requete->bindParam(1,$cin );
    $requete->execute(); 
    return $requete->fetchAll(); 
    
}

function getEmailPersonne($cin){
    global $bdd;
    $requete = $bdd->prepare("SELECT * from personne where cin = ? "); 
    $requete->bindParam(1,$cin ); 
    $requete->execute();
    $ligne = $requete->fetch();
    return $ligne['email']; 
}


function insertCode($cin){
    global $bdd; 
    $id = uniqid(); 
    $requete = $bdd->prepare("INSERT INTO registration_codes values(?,?)"); 
    $requete->bindParam(1,$cin );
    $requete->bindParam(2,$id ); 

    if($requete->execute()) return true; 
    return false; 
}

function getCode($cin){
    global $bdd; 
    $requete = $bdd->prepare("SELECT * from registration_codes where personne = ? ");
    $requete->bindParam(1,$cin ); 
    $requete->execute(); 
    $ligne =  $requete->fetch();
    return $ligne['code']; 
}


function isCodePersonne($cin, $code){
    global $bdd; 
    $requete = $bdd->prepare("SELECT * from registration_codes where personne = ? and code = ? "); 
    $requete->bindParam(1,$cin ); 
    $requete->bindParam(2,$code); 
    $requete->execute(); 
    if($requete->rowCount()>0) return true; 
    return false; 
}


function updateToken($token, $cin){
    global $bdd; 
    $requete = $bdd->prepare("UPDATE firebase_users SET cin = ? where token = ?"); 
    $requete->bindParam(1,$cin ); 
    $requete->bindParam(2, $token); 
    if($requete->execute()) return true; 
    return false; 
}



