// ### Modal handling ###
// Function to open modals
function openModal(modalId) {
    document.getElementById(modalId).style.display = "block";
}

// Function to close modals
function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// Function to close modals on window click
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = "none";
    }
}



// ### Forms submissions handling ###
// # Password changing form #
function handlePasswordForm(event) {
    event.preventDefault();

    const newPassword = document.getElementById('newPassword').value;
    const cfNewPassword = document.getElementById('cfNewPassword').value;

    if (newPassword && cfNewPassword) {
        if (newPassword === cfNewPassword) {
            fetch('/Profile/api/changePassword', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ newPassword : newPassword })
            })
                .then(response => handleResponse(response))
                .catch(error => handleError(error));
        } else {
            document.getElementById('error-message').textContent = "Passwords don't match";
            document.getElementById('error-message').style.display = 'block';
        }
    } else {
        document.getElementById('error-message').textContent = 'Please fill all fields.';
        document.getElementById('error-message').style.display = 'block';
    }
}

// Function to handle the response from the API
function handleResponse(response) {
    if (response.ok) {
        // If the response status is OK (200), process the response body as JSON:
        return response.json().then(data => {
            if (data.success) {
                window.location.href = '/Profile'; // Redirect to profile
            }
        });
    }  else {
        // Handle other unexpected responses:
        document.getElementById('error-message').textContent = 'An unexpected error occurred. Please try again later.';
        document.getElementById('error-message').style.display = 'block';
    }
}

// Function to handle errors
function handleError(error) {
    console.error('Error:', error);
    document.getElementById('error-message').textContent = 'An unexpected error occurred. Please try again later.';
    document.getElementById('error-message').style.display = 'block';
}

document.getElementById('passwordForm').addEventListener('submit', handlePasswordForm)