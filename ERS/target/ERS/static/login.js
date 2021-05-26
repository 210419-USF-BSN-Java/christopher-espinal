document.getElementById("loginForm").addEventListener("submit", fetchLogin);

function fetchLogin() {
    alert("New Login!")
    let form = document.getElementById("loginForm")
    let _user = {
        "username": form.elements['username'].value,
        "password": form.elements['password'].value,
    }
    console.log(_user);
    let url = "http://localhost:8080/ERS/user/login";

    let data = fetch(url, {
        method: "POST",
        body: JSON.stringify(_user),
        headers: {
            mode: "cors",
        }
    }).then(response => {
        if (response.status < 300 && response.status >= 200) {
            alert("Successfully Logged In! - New ");
            return response;
        } else {
            document.getElementById("message").innerHTML = "Please Try Again!";
            return window.location.replace(url);
        }
    }).catch(e => console.error(e));
}



/*     {
        console.log("Getting a response");
        if (response.status >= 200 && response.status < 400) {
            window.location.href = "http://localhost:8080/ERS";
        } else {
            document.getElementById("message").innerHTML = "Login Unsuccessful";
        } */