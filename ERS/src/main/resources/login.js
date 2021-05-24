document.getElementById("loginBtn").addEventListener("click", fetchLogin);

function fetchLogin() {
    console.log("Reached Beginning of Fetch Login");
    let form = document.getElementById("loginForm")
    let _user = {
        "username": form.elements['username'].value,
        "password": form.elements['password'].value,
    }

    let url = "http://localhost:8080/ERS/user/login";

    let response = fetch(url, {
        method: "POST",
        body: JSON.stringify(_user),
        mode: "no-cors",
        headers: {
            "Content-Type": "application/json",
        }
    }).then(response => console.log(response.status)).catch(e => log.error(e));


}



/*     {
        console.log("Getting a response");
        if (response.status >= 200 && response.status < 400) {
            window.location.href = "http://localhost:8080/ERS";
        } else {
            document.getElementById("message").innerHTML = "Login Unsuccessful";
        } */