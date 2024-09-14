function updateUserRole(event) {
    var checkbox = event.target;
    var userId = checkbox.getAttribute('data-user-id');
    var role = checkbox.checked ? 'ROLE_ADMIN' : 'ROLE_USER';

    var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/home/users/' + userId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken  // Добавление CSRF-токена в заголовок
        },
        body: JSON.stringify({ eRole: role })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('User role updated:', data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}