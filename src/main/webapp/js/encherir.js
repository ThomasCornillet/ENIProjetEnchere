/**
 * Récupération de l'enchère afin de l'afficher dans la fenêtre modale
 */
 
 var enchere = document.getElementById("encherir")
 var enchereConfirmation = document.getElementById("enchereenchereConfirmation")
 

function confirmerEnchere() {
    var result = confirm("Do you want to continue?");

              if(result)  {
                  alert("OK Next lesson!");
              } else {
                  alert("Bye!");
              }
}
