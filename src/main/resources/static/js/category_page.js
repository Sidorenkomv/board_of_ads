let categories;
let oldNameCategory;

$(document).ready(function () {
    showCategories();
});

$('#saveCategoryButton').on('click', function() {
    data = {
        id: $('#categoryID').val(),
        name: $('#categoryName').val(),
        parentName: $('#parent-category-select').val()
    }
    categoryService.updateCategory(oldNameCategory, data).then(categoryResponse => {
        if (categoryResponse.status === 200) {
            $('#updateCategoryModal').modal('hide');
            $('.list-group-item').remove();
            showCategories();
        }
    });
});

$('#addCategory').on('click', function() {
    $('#addCategoryModal').modal('show');
    $('select option').remove();
    let categorySelect = $('#parent-add-select');
    categories.then(categories => {
        categories.data.forEach((cat) => {
            if (cat.parentName == null) {
                let option = `<option name="${cat.name}" style="background: #b3cccc; color: #fff;">${cat.name}</option>`;
                categorySelect.append(option);
            } else {
                let option = `<option name="${cat.name}">${cat.name.substring(cat.parentName.length + 1)}</option>`;
                categorySelect.append(option);
            }
        });
        let option = `<option  th:value=null selected>Без категории</option>`;
        categorySelect.append(option);
    });
});

$('#addCategoryButton').on('click', function() {
    let data = {
        name: $('#categoryAddName').val(),
        parentName: $('#parent-add-select').val() === 'Без категории' ? '' : $('#parent-add-select').val()
    }
    categoryService.createCategory(data).then(categoryResponse => {
        if (categoryResponse.status === 200) {
            $('#addCategoryModal').modal('hide');
            $('.list-group-item').remove();
            showCategories();
        }
    });
});

async function deleteCategory() {
    categoryService.deleteCategory($('#categoryDeleteID').val()).then(
        categoryResponse => {
            if (categoryResponse.status === 200) {
                $('#deleteCategoryModal').modal('hide');
                $('.list-group-item').remove();
                showCategories();
            }
        }
    );
}

async function showCategories() {
    let categoriesResponse = await categoryService.findAllCategories();
    categories = categoriesResponse.json();
    let categoryList = $('#listCategory');
    categories.then(categories => {
        categories.data.forEach((cat) => {
            if (cat.parentName == null) {
                let rowCategory = `<li class="list-group-item list-group-item-light" th:text="${cat.name}">
                                    <div class="far fa-edit" onclick="showEditCategoryModal(${cat.id})"></div>
                                    <div class="far fa-trash-alt mr-2" onclick="showDeleteCategoryModal(${cat.id})"></div>${cat.name}</li>`;
                categoryList.append(rowCategory);
            } else {
                let rowWithLayerCategory;
                if (cat.layer === 2) {
                    rowWithLayerCategory= `<ul><li class="list-group-item list-group-item-light">
                                    <div class="far fa-edit" onclick="showEditCategoryModal(${cat.id})"></div>
                                    <div class="far fa-trash-alt mr-2" onclick="showDeleteCategoryModal(${cat.id})"></div>
                                    ${cat.name.split(":")[1]}
                                    </li></ul>`;
                }
                if (cat.layer === 3) {
                    rowWithLayerCategory = `<ul><ul><li class="list-group-item list-group-item-light">
                                    <div class="far fa-edit" onclick="showEditCategoryModal(${cat.id})"></div>
                                    <div class="far fa-trash-alt mr-2" onclick="showDeleteCategoryModal(${cat.id})"></div>
                                   ${cat.name.split(":")[2]}
                                    </li></ul></ul>`;
                }
                categoryList.append(rowWithLayerCategory);
            }
        })
    });
}

async function showEditCategoryModal(id) {
    let categoryResponse = await categoryService.findCategoryById(id);
    let categoryJson = categoryResponse.json();
    $('#updateCategoryModal').modal('show');
    categoryJson.then(category => {
        console.log(category);
        $('#categoryID').val(category.data.id);
        if (category.data.layer > 1) {
            $('#categoryName').val(category.data.name.substring(category.data.parentName.length + 1));
            oldNameCategory = category.data.name.substring(category.data.parentName.length + 1);
        } else {
            $('#categoryName').val(category.data.name);
            oldNameCategory = category.data.name;
        }
        let categorySelect = $('#parent-category-select');
        $('select option').remove();
        categories.then(categories => {
            let optionNull;
            if (category.data.layer === 1) {
                optionNull = `<option th:value=null selected></option>`;
            } else {
                optionNull = `<option th:value=null></option>`;
            }
            categorySelect.append(optionNull);
            categories.data.forEach((cat) => {
                let option;
                    if (category.data.parentName === cat.name) {
                        if (cat.layer > 1) {
                            option = `<option  th:value="${cat.name}" selected>${cat.name.substring(cat.parentName.length + 1)}</option>`;
                        } else {
                            option = `<option  th:value="${cat.name}" selected>${cat.name}</option>`;
                        }
                    } else {
                        if (cat.layer > 1) {
                            option = `<option  th:value="${cat.name}">${cat.name.substring(cat.parentName.length + 1)}</option>`;
                        } else {
                            option = `<option  th:value="${cat.name}">${cat.name}</option>`;
                        }
                    }
                categorySelect.append(option);
            });
        });
    });
}

async function showDeleteCategoryModal(catName) {
    $('#deleteCategoryModal').modal('show');
    let categoryResponse = await categoryService.findCategoryById(catName);
    let categoryJson = categoryResponse.json();
    categoryJson.then(category => {
        $('#categoryDeleteID').val(category.data.id);
        if (category.data.layer > 1) {
            $('#categoryDeleteName').val(category.data.name.substring(category.data.parentName.length + 1));
        } else {
            $('#categoryDeleteName').val(category.data.name);
        }
        if (category.data.layer < 3) {
            $('#categoryParentName').val(category.data.parentName);
        } else {
            $('#categoryParentName').val(category.data.parentName.split(":")[1]);
        }
    });
}

const http = {
    fetch: async function(url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });
        return response;
    }
};

const categoryService = {
    findAllCategories: async () => {
        return await http.fetch('/api/category', {
            method: 'GET'
        });
    },
    findCategoryById: async (id) => {
        return await http.fetch('/api/category/' + id);
    },
    updateCategory: async (oldNameCategory, data) => {
        return await http.fetch('/api/category/' + oldNameCategory, {
            body: JSON.stringify(data),
            method: 'PUT'
        });
    },
    deleteCategory: async (id) => {
        return await http.fetch('/api/category/' + id, {
            method: 'DELETE'
        });
    },
    createCategory: async (data) => {
        return await http.fetch('/api/category', {
            body: JSON.stringify(data),
            method: 'POST'
        });
    },
}