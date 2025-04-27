import API_URL from '../config/api-config.js';

async function submitUserForm(event) {
    console.log("chamando submit");
    event.preventDefault();

    const name = document.getElementById('user-name').value;
    const email = document.getElementById('user-email').value;
    const userData = { name, email };

    const userId = event.target.dataset.userId;
    const method = userId ? 'PUT' : 'POST';
    const url = userId ? `${API_URL}/users/${userId}` : `${API_URL}/users`;

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        const result = await response.json();
        if (response.ok) {
            alert('User saved successfully');
            getUsers(); // Reload the users list
        } else {
            alert('Error saving user: ' + result.message);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Something went wrong.');
    }
}

async function deleteUser(id) {
    if (confirm(`Are you sure you want to delete user with ID: ${id}?`)) {
        try {
            const response = await fetch(`${API_URL}/users/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('User deleted successfully');
                getUsers();
            } else {
                alert('Failed to delete user');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Something went wrong.');
        }
    }
}

async function getUsers() {
    try {
        const response = await fetch(`${API_URL}/users`);
        const users = await response.json();

        const userList = document.getElementById('user-list');
        userList.innerHTML = '';

        users.forEach(user => {
            const listItem = document.createElement('li');
            listItem.innerHTML = `${user.name} - ${user.email} <button onclick="deleteUser(${user.id})">Delete</button>`;
            userList.appendChild(listItem);
        });
    } catch (error) {
        console.error('Error fetching users:', error);
    }
}

document.getElementById('user-form').addEventListener('submit', submitUserForm);

getUsers();
