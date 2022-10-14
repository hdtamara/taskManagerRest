$(document).ready(function() {
    getTasks();
    getUsers();
    //$('#tasklist').DataTable();    
  });

  function getHeaders(){
    return {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization': localStorage.token
          };
  }

  async function getTasks(){


    const request = await fetch('api/task', {
      method: 'GET',  
      headers: getHeaders(),
    });
    const tasks = await request.json();
  
      

    
    let listadoHtml = '';
    for (let task of tasks){      
      let deleteButton = '<a href="#!" onclick ="deleteTask('+task.id+')" data-mdb-toggle="tooltip" title="Remove"><i class="material-icons md-18 text-danger">delete</i></a';
      let doneButton = '<a href="#!" onclick ="updateTask('+task.id+')" data-mdb-toggle="tooltip" title="Done"><i class="material-icons md-18 text-success">done</i></a>';

      let action = !task.done ? doneButton + deleteButton : deleteButton;
      let status = !task.done ? '<h6 class="mb-0"><span class="badge bg-danger">WAITING</span></h6>':'<h6 class="mb-0"><span class="badge bg-success">COMPLETED</span></h6>'
      let taskHtml =  ' <tr class="fw-normal"> <th> <img src='+task.user.image+' class="shadow-1-strong rounded-circle" alt="avatar 1" style="width: 55px; height: auto;">      <span class="ms-2">'+task.user.name+' '+task.user.lastName +'</span> </th>    <td class="align-middle">      <span>'+task.description +'</span>    </td> <td class="align-middle">      <h6 class="mb-0"><span class="badge bg-danger">'+ task.dateFinish+'</span></h6> </td>    <td class="align-middle">'+action+'</td><td class="align-middle">'+status+'</td></tr>';

      listadoHtml += taskHtml;
    }
  
    document.querySelector('#tasklist tbody').outerHTML = listadoHtml;
  
 
  }

 async function deleteTask(id){

    if(!confirm("¿Desea eliminar la tarea?")){
      return;
    }

    const request = await fetch('api/task/'+id, {
        method: 'DELETE',    
        headers: getHeaders(),
      });

      location.reload()

};

async function updateTask(id){

    if(!confirm("¿Desea finalizar la tarea?")){
      return;
    }

    const request = await fetch('api/task/'+id, {
        method: 'PUT',    
        headers: getHeaders(),
      });   

      location.reload()

};

async function createTask(){
   

    let datos = {};    ;
    datos.description = document.getElementById('description').value;
    datos.dateFinish = document.getElementById('dateFinish').value;
    datos.user = document.getElementById('user').value ;

    if(datos.user == 0){
        datos.user = null;
     } else
     datos.user =  {
           id: document.getElementById('user').value
        }    
   
    const request = await fetch('api/task', {
      method: 'POST',
      headers: getHeaders(),

      body: JSON.stringify(datos)
    });
    location.reload()
}

async function getUsers(){
    const request = await fetch('api/user', {
        method: 'GET',  
        headers: getHeaders(),  
      });
      const users = await request.json();
      if (users == null){
        alert("Pleaase Login")
        window.location.href = 'login.html'

      }
      const select = document.getElementById('user');
      const selectFind = document.getElementById('FindByUsers');

      users.forEach((user, idx) => {
          const keyx = user + idx
          const option = document.createElement('option');
          option.setAttribute("key", keyx);
          option.setAttribute('value', user.id);
          const optionText = document.createTextNode(user.name+' '+user.lastName);
          option.appendChild(optionText);
          selectFind.appendChild(option);
          
          
      })

      users.forEach((user, idx) => {
        const keyx = user + idx
        const option = document.createElement('option');
        option.setAttribute("key", keyx);
        option.setAttribute('value', user.id);
        const optionText = document.createTextNode(user.name+' '+user.lastName);
        option.appendChild(optionText);        
        select.appendChild(option);
        
    })
   
}

async function getTasksByUser(){

  let idUser = document.getElementById('FindByUsers').value ;

  const request = await fetch('api/task/'+idUser, {
    method: 'GET',  
    headers: getHeaders(),
  });
  const tasks = await request.json();

    

  
  let listadoHtml = '';
  for (let task of tasks){      
    let deleteButton = '<a href="#!" onclick ="deleteTask('+task.id+')" data-mdb-toggle="tooltip" title="Remove"><i class="material-icons md-18 text-danger">delete</i></a';
    let doneButton = '<a href="#!" onclick ="updateTask('+task.id+')" data-mdb-toggle="tooltip" title="Done"><i class="material-icons md-18 text-success">done</i></a>';

    let action = !task.done ? doneButton + deleteButton : deleteButton;
    let status = !task.done ? '<h6 class="mb-0"><span class="badge bg-danger">WAITING</span></h6>':'<h6 class="mb-0"><span class="badge bg-success">COMPLETED</span></h6>';
    let taskHtml =  ' <tr class="fw-normal"> <th> <img src='+task.user.image+' class="shadow-1-strong rounded-circle" alt="avatar 1" style="width: 55px; height: auto;">      <span class="ms-2">'+task.user.name+' '+task.user.lastName +'</span> </th>    <td class="align-middle">      <span>'+task.description +'</span>    </td> <td class="align-middle">      <h6 class="mb-0"><span class="badge bg-danger">'+ task.dateFinish+'</span></h6> </td>    <td class="align-middle">'+action+'</td> <td class="align-middle">'+status+'</td></tr>';

    listadoHtml += taskHtml;
  }

  document.querySelector('#tasklist tbody').innerHTML = listadoHtml;


}