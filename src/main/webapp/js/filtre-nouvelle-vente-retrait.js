/**
 * 
 */
 
 var ckRetrait = document.getElementById("checkRetrait");
 var inputRue = document.getElementById("rue");
 var inputCodePostal = document.getElementById("code_postal");
 var inputVille = document.getElementById("ville");

 ckRetrait.addEventListener("change", function(event) {
	if (event.target.checked) {
		inputRue.disabled = false;
		inputCodePostal.disabled = false;
		inputVille.disabled = false;
		
		inputRue.required = true;
		inputCodePostal.required = true;
		inputVille.required = true;
	} else {
		inputRue.disabled = true;
		inputCodePostal.disabled = true;
		inputVille.disabled = true;
		
		inputRue.required = false;
		inputCodePostal.required = false;
		inputVille.required = false;
	}
}, false);