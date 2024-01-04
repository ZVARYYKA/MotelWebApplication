function showEditForm(button) {
    // Находим родительский элемент, содержащий форму и кнопку
    const parentElement = button.parentElement;

    // Находим форму редактирования внутри родительского элемента
    const editForm = parentElement.querySelector('.edit-form');

    // Переключаем стиль отображения формы (показываем/скрываем)
    if (editForm.style.display === 'none' || editForm.style.display === '') {
        editForm.style.display = 'block';
    } else {
        editForm.style.display = 'none';
    }
}