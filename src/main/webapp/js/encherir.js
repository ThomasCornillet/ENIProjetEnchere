/**
 * Récupération de l'enchère afin de l'afficher dans la fenêtre modale
 */
 
 var enchere = document.getElementById("encherir")
 var enchereConfirmation = document.getElementById("enchereenchereConfirmation")
 

function confirmerEnchere() {
  let text = "Votre enchère est égale à :" + enchere + "\nOK pour confirmer.";
  if (confirm(text) == true) {
    text = "You pressed OK!";
  } else {
    text = "You canceled!";
  }
  document.getElementById("encherir").innerHTML = text;
}
