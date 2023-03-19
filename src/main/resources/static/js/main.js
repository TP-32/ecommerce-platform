let list = document.querySelectorAll(".navigation li");

function activeLink() {
  list.forEach((item) => {
    item.classList.remove("hovered");
  });
  this.classList.add("hovered");
}

list.forEach((item) => item.addEventListener("mouseover", activeLink));

let toggle = document.querySelector(".toggle");
let navigation = document.querySelector(".navigation");
let main = document.querySelector(".main");

toggle.onclick = function () {
  navigation.classList.toggle("active");
  main.classList.toggle("active");
};

function onFormSubmit() {
  var formData = readFormData();


}

var selectedRow = null

function onFormSubmit(e) {
	event.preventDefault();
        var formData = readFormData();
        if (selectedRow == null){
            insertNewRecord(formData);
		}
        else{
            updateRecord(formData);
		}
        resetForm();    
}

function readFormData() {
    var formData = {};
    formData["productCode"] = document.getElementById("productCode").value;
    formData["product"] = document.getElementById("product").value;
    formData["product"] = document.getElementById("product").value;
    formData["perPrice"] = document.getElementById("perPrice").value;
    return formData;
}


function insertNewRecord(data) {
    var table = document.getElementById("storeList").getElementsByTagName('tbody')[0];
    var newRow = table.insertRow(table.length);
    cell1 = newRow.insertCell(0);
		cell1.innerHTML = data.productCode;
    cell2 = newRow.insertCell(1);
		cell2.innerHTML = data.product;
    cell3 = newRow.insertCell(2);
		cell3.innerHTML = data.product;
    cell4 = newRow.insertCell(3);
		cell4.innerHTML = data.numbers2;
    cell5 = newRow.insertCell(4);
		cell5.innerHTML = data.perPrice;
    cell6 = newRow.insertCell(5);
        cell6.innerHTML = `<button onClick="onEdit(this)">Edit</button> <button onClick="onDelete(this)">Delete</button>`;
}


function onEdit(td) {
    selectedRow = td.parentElement.parentElement;
    document.getElementById("productCode").value = selectedRow.cells[0].innerHTML;
    document.getElementById("product").value = selectedRow.cells[1].innerHTML;
    document.getElementById("product").value = selectedRow.cells[2].innerHTML;
    document.getElementById("numbers2").value = selectedRow.cells[3].innerHTML;
    document.getElementById("perPrice").value = selectedRow.cells[4].innerHTML;
}
function updateRecord(formData) {
    selectedRow.cells[0].innerHTML = formData.productCode;
    selectedRow.cells[1].innerHTML = formData.product;
    selectedRow.cells[2].innerHTML = formData.product;
    selectedRow.cells[3].innerHTML = formData.numbers2;
    selectedRow.cells[4].innerHTML = formData.perPrice;
}


function onDelete(td) {
    if (confirm('Do you want to delete this record?')) {
        row = td.parentElement.parentElement;
        document.getElementById('storeList').deleteRow(row.rowIndex);
        resetForm();
    }
}


function resetForm() {
    document.getElementById("productCode").value = '';
    document.getElementById("product").value = '';
    document.getElementById("product").value = '';
    document.getElementById("numbers2").value = '';
    document.getElementById("perPrice").value = '';
    selectedRow = null;
}