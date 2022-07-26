/**
 * 	gestion affichage checkbox filtres page d'accueil
 */
 
var achatsRadio = document.getElementById("radioAchat");
var ventesRadio = document.getElementById("radioMesVentes");
var ckEncheresOuvertes = document.getElementById("encheresOuvertes");
var ckEncheresEnCours = document.getElementById("encheresEnCours");
var ckEncheresRemportees = document.getElementById("encheresRemportees");
var ckVentesEnCours = document.getElementById("ventesEnCours");
var ckVentesNonDebutees = document.getElementById("ventesNonDebutees");
var ckVentesTerminees = document.getElementById("ventesTerminees");

achatsRadio.addEventListener("change", function(event) {
  if (event.target.checked) {
    ckEncheresOuvertes.disabled = false;
    ckEncheresEnCours.disabled = false;
    ckEncheresRemportees.disabled = false;

    ckVentesEnCours.disabled = true;
    ckVentesNonDebutees.disabled = true;
    ckVentesTerminees.disabled = true;

    ckVentesEnCours.checked = false;
    ckVentesNonDebutees.checked = false;
    ckVentesTerminees.checked = false;
  }
}, false);

ventesRadio.addEventListener("change", function(event) {
  if (event.target.checked) {
    ckEncheresOuvertes.disabled = true;
    ckEncheresEnCours.disabled = true;
    ckEncheresRemportees.disabled = true;

    ckVentesEnCours.disabled = false;
    ckVentesNonDebutees.disabled = false;
    ckVentesTerminees.disabled = false;

    ckEncheresOuvertes.checked = false;
    ckEncheresEnCours.checked = false;
    ckEncheresRemportees.checked = false;
  }
}, false);
