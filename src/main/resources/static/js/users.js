
document.addEventListener('DOMContentLoaded',getUsers)
function getHeaders() {
  return {
    Accept: "application/json",
    "Content-Type": "application/json",
    Authorization: localStorage.token,
  };
}

async function getUsers() {
  const request = await fetch("/admin/users", {
    method: "GET",
    headers: getHeaders(),
  });

  if(!request.ok){
    alert("Por favor inicie sesión")
    location.href = "/"
  
  }
  const users = await request.json();

  let listadoHtml = "";
  for (let user of users) {
    let deleteButton =
      '<a href="#!" onclick ="statusUser(' +
      user.id +
      ')" data-mdb-toggle="tooltip" title="Deshabilitar"><i class="material-icons md-18 text-danger">block </i></a';
    let doneButton =
      '<a href="#!" onclick ="statusUser(' +
      user.id +
      ')" data-mdb-toggle="tooltip" title="Habiliar"><i class="material-icons md-18 text-success">done_outline</i></a>';  
      
      let editButton =
      '<a href="#!" onclick ="getUser(' +
      user.id +
      ')" data-mdb-toggle="tooltip" data-bs-toggle="modal" data-bs-target="#addModal" title="Editar"><i class="material-icons md-18 text-success">edit_note</i></a>';

    let action = user.enabled ? deleteButton : doneButton;
    let fullName = !user.lastName == '' ? user.name +
    " " +
    user.lastName : user.name;

    let status = user.enabled ? '<h6 class="mb-0"><span class="badge bg-success">ACTIVO</span></h6>':'<h6 class="mb-0"><span class="badge bg-danger">INACTIVO</span></h6>'
  
    let userHtml =
      ' <tr class="fw-normal"> <th> <img src=' +
      user.image +
      ' class="shadow-1-strong rounded-circle" alt="avatar 1" style="width: 55px; height: auto;">      <span class="ms-2">' +
      fullName +
      '</span> </th>    <td class="align-middle">      <span>' +
      user.email +
      '</span>    </td> <td class="align-middle">      <h6 class="mb-0"><span class="badge">' +
      status+
      '</span></h6> </td>    <td class="align-middle">' +
      editButton +
      '</td><td class="align-middle">' +
      action +
      "</td></tr>";

    listadoHtml += userHtml;
  }

  document.querySelector("#tasklist tbody").outerHTML = listadoHtml;
}

async function statusUser(id){

    if(!confirm("¿Desea modificar el estado del empleado?")){
      return;
    }

    const request = await fetch('/admin/status/'+id, {
        method: 'PUT',    
        headers: getHeaders(),
      });   

      location.reload()

};

async function getUser(id) {
  const request = await fetch("/admin/user/"+id, {
    method: "GET",
    headers: getHeaders(),
  });
  const user = await request.json();

  let name =document.getElementById("EditNameInput");
  let lastName = document.getElementById("EditLastNameInput");
  let email = document.getElementById("EditEmailInput");
  let image = document.getElementById("EditImagenInput");
  let idUser = document.getElementById("EditIdInput");
  

name.value = user.name;
lastName.value = user.lastName;
email.value = user.email;
image.value = user.image; 
idUser.value = user.id; 

}

async function editUser(){
   

  let datos = {};    
  let id = document.getElementById("EditIdInput").value;
  datos.name =document.getElementById("EditNameInput").value;
  datos.lastName= document.getElementById("EditLastNameInput").value;
  datos.email  = document.getElementById("EditEmailInput").value;
  datos.image = document.getElementById("EditImagenInput").value; 

 
  const request = await fetch('/admin/user/'+id, {
    method: 'PUT',
    headers: getHeaders(),

    body: JSON.stringify(datos)
  });
  location.reload()
}