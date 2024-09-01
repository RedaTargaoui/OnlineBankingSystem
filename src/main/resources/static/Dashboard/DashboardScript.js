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
// # Deposit form #
function handleDepositForm(event) {
    event.preventDefault();

    const amount = document.getElementById('depositAmount').value;

    if (amount && amount > 0) {
        fetch('/Dashboard/api/deposit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ amount: amount })
        })
            .then(response => handleResponse(response, "deposit"))
            .catch(error => handleError(error, "deposit"));
    } else {
        document.getElementById('depositErrorMessage').textContent = 'Please enter a valid amount.';
        document.getElementById('depositErrorMessage').style.display = 'block';
    }
}

// # Withdraw form #
function handleWithdrawForm(event) {
    event.preventDefault();

    const amount = document.getElementById('withdrawAmount').value;

    if (amount && amount > 0) {
        fetch('/Dashboard/api/withdraw', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ amount: amount })
        })
            .then(response => handleResponse(response, "withdraw"))
            .catch(error => handleError(error, "withdraw"));
    } else {
        document.getElementById('withdrawErrorMessage').textContent = 'Please enter a valid amount.';
        document.getElementById('withdrawErrorMessage').style.display = 'block';
    }
}

// # Transfer form #
function handleTransferForm(event) {
    event.preventDefault();

    const amount = document.getElementById('transferAmount').value;
    const recipient = document.getElementById('recipientAccount').value;

    if (amount && amount > 0) {
        if (recipient) {
            fetch('/Dashboard/api/transfer', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ amount: amount, recipient: recipient })
            })
                .then(response => handleResponse(response, "transfer"))
                .catch(error => handleError(error, "transfer"));
        } else {
            document.getElementById('transferErrorMessage').textContent = 'Please enter the full name of the recipient.';
            document.getElementById('transferErrorMessage').style.display = 'block';
        }
    } else {
        document.getElementById('transferErrorMessage').textContent = 'Please enter a valid amount.';
        document.getElementById('transferErrorMessage').style.display = 'block';
    }
}

// # Common #
// Function to handle the response from API:
function handleResponse(response, type) {
    if (type === "deposit") {
        if (response.ok) {
            // If the response status is OK (200), process the response body as JSON:
            return response.json().then(data => {
                if (data.success) {
                    window.location.href = '/Dashboard/'; // Redirect to dashboard
                }
            });
        } else {
            // Handle other unexpected responses:
            document.getElementById('depositErrorMessage').textContent = 'An unexpected error occurred. Please try again later.';
            document.getElementById('depositErrorMessage').style.display = 'block';
        }
    } else if (type === "withdraw") {
        if (response.ok) {
            // If the response status is OK (200), process the response body as JSON:
            return response.json().then(data => {
                if (data.success) {
                    window.location.href = '/Dashboard/'; // Redirect to dashboard
                }
            });
        } else if (response.status === 401) {
            // Handle unauthorized response
            document.getElementById('withdrawErrorMessage').textContent = "You don't have this amount in your balance" +
                ", please enter a valid amount";
            document.getElementById('withdrawErrorMessage').style.display = 'block';
        } else {
            // Handle other unexpected responses:
            document.getElementById('withdrawErrorMessage').textContent = 'An unexpected error occurred. Please try again later.';
            document.getElementById('withdrawErrorMessage').style.display = 'block';
        }
    } else {
        if (response.ok) {
            // If the response status is OK (200), process the response body as JSON:
            return response.json().then(data => {
                if (data.success) {
                    window.location.href = '/Dashboard/'; // Redirect to dashboard
                }
            });
        } else if (response.status === 401) {
            // Handle unauthorized response
            document.getElementById('transferErrorMessage').textContent = "You don't have this amount in your balance" +
                ", please enter a valid amount";
            document.getElementById('transferErrorMessage').style.display = 'block';
        } else if (response.status === 404) {
            // Handle not found response
            document.getElementById('transferErrorMessage').textContent = "The entered recipient account doesn't exists";
            document.getElementById('transferErrorMessage').style.display = 'block';
        } else {
            // Handle other unexpected responses:
            document.getElementById('transferErrorMessage').textContent = 'An unexpected error occurred. Please try again later.';
            document.getElementById('transferErrorMessage').style.display = 'block';
        }
    }
}

// Function to handle errors
function handleError(error, type) {
    console.error('Error:', error);
    if (type === 'deposit') {
        document.getElementById('depositErrorMessage').textContent = 'An unexpected error occurred. Please try again later.';
        document.getElementById('depositErrorMessage').style.display = 'block';
    } else if (type === 'withdraw') {
        document.getElementById('withdrawErrorMessage').textContent = 'An unexpected error occurred. Please try again later.';
        document.getElementById('withdrawErrorMessage').style.display = 'block';
    } else {
        document.getElementById('transferErrorMessage').textContent = 'An unexpected error occurred. Please try again later.';
        document.getElementById('transferErrorMessage').style.display = 'block';
    }
}

document.getElementById('depositForm').addEventListener('submit', handleDepositForm)
document.getElementById('withdrawForm').addEventListener('submit', handleWithdrawForm)
document.getElementById('transferForm').addEventListener('submit', handleTransferForm)