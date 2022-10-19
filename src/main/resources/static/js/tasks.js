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
    if(localStorage.length == 0){
      alert("Plase login")
      location.href='/'
    }

   else if(localStorage.rol.includes("ADMIN")){

      document.getElementById('goToUsers').hidden = false;
      const request = await fetch('/task/admin', {
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
        let taskHtml =  ' <tr class="fw-normal"> <th> <img src='+task.user.image+' class="shadow-1-strong rounded-circle" alt="avatar 1" style="width: 55px; height: auto;">      <span class="ms-2">'+task.user.name+' '+task.user.lastName +'</span> </th>    <td class="align-middle">      <span>'+task.description +'</span>    </td> <td class="align-middle">      <h6 class="mb-0"><span class="badge bg-danger">'+ task.finishDate+'</span></h6> </td>    <td class="align-middle">'+action+'</td><td class="align-middle">'+status+'</td></tr>';
  
        listadoHtml += taskHtml;
      }
    
      document.querySelector('#tasklist tbody').outerHTML = listadoHtml;


    } else if(localStorage.rol.includes("USER")){

      const request = await fetch('/task/'+localStorage.email, {
        method: 'GET',  
        headers: getHeaders(),
      });
      const tasks = await request.json();
    
        
  
      
      let listadoHtml = '';
      for (let task of tasks){      
        
        let doneButton = '<a href="#!" onclick ="updateTask('+task.id+')" data-mdb-toggle="tooltip" title="Done"><i class="material-icons md-18 text-success">done</i></a>';
  
        let action = !task.done ? doneButton: "";
        let status = !task.done ? '<h6 class="mb-0"><span class="badge bg-danger">WAITING</span></h6>':'<h6 class="mb-0"><span class="badge bg-success">COMPLETED</span></h6>'
        let taskHtml =  ' <tr class="fw-normal"> <th> <img src='+task.user.image+' class="shadow-1-strong rounded-circle" alt="avatar 1" style="width: 55px; height: auto;">      <span class="ms-2">'+task.user.name+' '+task.user.lastName +'</span> </th>    <td class="align-middle">      <span>'+task.description +'</span>    </td> <td class="align-middle">      <h6 class="mb-0"><span class="badge bg-danger">'+ task.finishDate+'</span></h6> </td>    <td class="align-middle">'+action+'</td><td class="align-middle">'+status+'</td></tr>';
  
        listadoHtml += taskHtml;
      }
      document.getElementById("footerAddTask").innerHTML = '';
      document.getElementById("findbyuser").innerHTML = '';
    
      document.querySelector('#tasklist tbody').outerHTML = listadoHtml;

    } 


   
  
 
  }

 async function deleteTask(id){

    if(!confirm("¿Desea eliminar la tarea?")){
      return;
    }

    const request = await fetch('/task/admin/'+id, {
        method: 'DELETE',    
        headers: getHeaders(),
      });

      location.reload()

};

async function updateTask(id){

    if(!confirm("¿Desea finalizar la tarea?")){
      return;
    }

    const request = await fetch('/task/'+id, {
        method: 'PUT',    
        headers: getHeaders(),
      });   

      location.reload()

};

async function createTask(){
   

    let datos = {};    ;
    datos.description = document.getElementById('description').value;
    datos.finishDate = document.getElementById('dateFinish').value;
    datos.userId = document.getElementById('user').value ;

   
   
    const request = await fetch('/task/admin', {
      method: 'POST',
      headers: getHeaders(),

      body: JSON.stringify(datos)
    });
    location.reload()
}

async function getUsers(){
    const request = await fetch('/admin/users', {
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
          option.setAttribute('value', user.email);
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

  const request = await fetch('/task/'+idUser, {
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
    let taskHtml =  ' <tr class="fw-normal"> <th> <img src='+task.user.image+' class="shadow-1-strong rounded-circle" alt="avatar 1" style="width: 55px; height: auto;">      <span class="ms-2">'+task.user.name+' '+task.user.lastName +'</span> </th>    <td class="align-middle">      <span>'+task.description +'</span>    </td> <td class="align-middle">      <h6 class="mb-0"><span class="badge bg-danger">'+ task.finishDate+'</span></h6> </td>    <td class="align-middle">'+action+'</td> <td class="align-middle">'+status+'</td></tr>';

    listadoHtml += taskHtml;
  }

  document.querySelector('#tasklist tbody').innerHTML = listadoHtml;


}