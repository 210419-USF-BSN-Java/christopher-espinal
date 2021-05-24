document.getElementById("login-btn").addEventListener("click", requestLogin);
function requestLogin() {

	let user = document.getElementById("username").value;
	let pass = document.getElementById("password").value;

	let xhr = new XMLHttpRequest();
	let url = "http://localhost:8080/ERS/user/login";
	xhr.open("POST", url);

	xhr.onreadystatechange = function () {
		console.log("On Ready State Change is being executed");
		if (xhr.readyState == 4 && xhr.status == 200) {
			let auth = xhr.getResponseHeader("Authentication");

			let token = sessionStorage.setItem("token", auth);
			console.log(token);
			window.location.href = "http://localhost:8080/ERS/manager"
		}
		else if (xhr.readyState == 4) {
			document.getElementById('message').innerHTML = 'Incorrect credentials!';
		}
	}

	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	let requestBody = `username=${user}&password=${pass}`;
	xhr.send(requestBody);
}