function updateIsCompleted(event) {
    var checkbox = event.target;
    var taskId = checkbox.getAttribute('data-task-id');
    var isCompleted = checkbox.checked;

    var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/home/tasks/' + taskId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken  // Добавление CSRF-токена в заголовок
        },
        body: JSON.stringify({ completed: isCompleted })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Task updated:', data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}