document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("loginForm").addEventListener("submit", async function(event) {
        event.preventDefault();

        const userName = document.getElementById("userName").value;
        const password = document.getElementById("password").value;

        const response = await fetch("http://localhost:8080/client/public/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userName: userName,
                password: password
            })
        });

        try {
            if (!response.ok) {
                throw new Error("Invalid credentials");
            }

            const data = await response.json();

            if (data.token) {
                localStorage.setItem("jwtToken", data.token); // Store JWT token
                alert("Login successful!");
                window.location.href = "/products"; // Redirect after login
            } else {
                alert("Invalid credentials. Please try again.");
            }
        } catch (error) {
            alert(error.message);
        }
    });
});
