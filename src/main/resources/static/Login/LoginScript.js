// Function to handle form submission
function handleFormSubmission(event) {
    event.preventDefault(); // Prevent the form from submitting the default way

    const fullName = document.getElementById('fullName').value;
    const password = document.getElementById('password').value;

    if (fullName && password) {
        fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ fullName: fullName, password: password })
        })
            .then(response => handleLoginResponse(response))
            .catch(error => handleError(error));
    } else {
        document.getElementById('errorMessage').textContent = 'Please fill in all required fields.';
        document.getElementById('errorMessage').style.display = 'block';
    }
}

// Function to handle the response from the login API
function handleLoginResponse(response) {
    if (response.ok) {
        // If the response status is OK (200), process the response body as JSON:
        return response.json().then(data => {
            if (data.success) {
                window.location.href = '/Dashboard/'; // Redirect to dashboard
            }
        });
    } else if (response.status === 404) {
        // Handle NOT_FOUND (404) response:
        document.getElementById('errorMessage').textContent = 'Incorrect Full name/Password.';
        document.getElementById('errorMessage').style.display = 'block';
    } else {
        // Handle other unexpected responses:
        document.getElementById('errorMessage').textContent = 'An unexpected error occurred. Please try again later.';
        document.getElementById('errorMessage').style.display = 'block';
    }
}

// Function to handle errors
function handleError(error) {
    console.error('Error:', error);
    document.getElementById('errorMessage').textContent = 'An unexpected error occurred. Please try again later.';
    document.getElementById('errorMessage').style.display = 'block';
}

// Attach the form submission handler to the form
document.getElementById('loginForm').addEventListener('submit', handleFormSubmission);
