const loginForm = document.querySelector('form');
			 
 loginForm.addEventListener('submit' , event => {
	 
	 event.preventDefault();
	 const formData = new FormData(loginForm);
	 const data =  Object.fromEntries(formData);
	 
     fetch('http://localhost:8080/api/login',{
		 method : 'POST',
		 headers: {
			 'Content-Type' : 'application/json'
			 
		 },
		 body: JSON.stringify(data)
		 
	 }).then(res => res.json())
	   .then(data =>{
	      console.log(data);
          window.location.href = "http://localhost:8080/api/dashboard";
          //token needed here store it in http header
        })
	   .catch(error =>{ 
		 var element = document.getElementById("invalid");
         element.classList.remove("d-none");
		 console.log(error)
	   });
 });
 