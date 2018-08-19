
function fillEmail() {
    var emailElement = document.getElementById("email");
    var action = "YldGcGJIUnY=";
    var user = "Z290b25vZGU=";
    var domain = "b3V0bG9vay5jb20=";
    emailElement.href = atob(atob(action)) + ":" + atob(user) + atob("QA==") + atob(domain);
}

function del(path, id, id2) {

    var form = document.createElement("form");
    form.setAttribute("method", "POST");
    form.setAttribute("action", path);

    var idField = document.createElement("input");
    idField.setAttribute("type", "hidden");
    idField.setAttribute("name", "id");
    idField.setAttribute("value", id);

    form.appendChild(idField);

    if (id2 !== null && id2 !== "") {

        var idField2 = document.createElement("input");
        idField2.setAttribute("type", "hidden");
        idField2.setAttribute("name", "id2");
        idField2.setAttribute("value", id2);

        form.appendChild(idField2);
    }

    document.body.appendChild(form);

    form.submit();
}

function deleteConfirmRecipe(recipeId, name) {

    var c = confirm("Please confirm you wish to remove the following recipe:\n\n" + name);

    if (c === true) {
        del("/delete/recipe", recipeId);
    }
}

function deleteConfirmIngredient(ingredientId, name) {

    var c = confirm("Please confirm you wish to remove the following ingredient:\n\n" + name);

    if (c === true) {
        del("/delete/ingredient", ingredientId);
    }
}

function deleteConfirmIngredientFromRecipe(recipeId, ingredientId, name) {

    var c = confirm("Please confirm you wish to remove the following ingredient from the current recipe:\n\n" + name);

    if (c === true) {
        del("/delete/ingredientFromRecipe", recipeId, ingredientId);
    }
}

function validateForm() {

    var nodes = document.querySelectorAll("#form input[type=text]");

    for (var i = 0; i < nodes.length; i++) {

        if (nodes[i].value === "") {
            alert("All input fields must have values in them.");

            return false;
        }
    }
}

function processDisclaimer() {

    document.cookie = "disclaimer=true;path=/";
    document.getElementById("disclaimer").remove();

    return false;
}
