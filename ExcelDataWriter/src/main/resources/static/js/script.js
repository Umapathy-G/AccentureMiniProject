$('#chooseFile').bind('change', function() {
	var filename = $("#chooseFile").val();
	if (/^\s*$/.test(filename)) {
		$(".file-upload").removeClass('active');
		$("#noFile").text("No file chosen...");
	}
	else {
		$(".file-upload").addClass('active');
		$("#noFile").text(filename.replace("C:\\fakepath\\", ""));
	}
});

document.getElementById("inputuploadbtn").addEventListener("mouseover", mouseOver);
document.getElementById("inputuploadbtn").addEventListener("mouseout", mouseOut);

function mouseOver() {
  document.getElementById("ico").style.color = "black";
}

function mouseOut() {
  document.getElementById("ico").style.color = "white";
}

async function uploadFile() {
	let formData = new FormData();
	formData.append("file", chooseFile.files[0]);
	let response;
	var fileInput = document.getElementById('chooseFile');
	var filePath = fileInput.value;
	var allowedExtensions = /(\.xlsx)$/i;

	if (chooseFile.files[0] == null) {
		return alert("Upload Excel(.xlsx) file");
	}

	if (!allowedExtensions.exec(filePath)) {
		alert('Invalid file type');
		fileInput.value = '';
		return false;
	}

	if (response.status == 200) {
		alert("File successfully uploaded.");
	}

	/*		else {
				response = await fetch('/upload', {
					method: "POST",
					body: formData
				});
			}  */

}