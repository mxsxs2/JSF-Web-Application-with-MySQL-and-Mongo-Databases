$(document).ready(function() {
	// Turn every table into sortable table
	$('table').DataTable();
	
	// Add add country button listener
	$('body').on('click', '#addcountrybutton', function() {
		//Load the content of add_country.xhtml into a hidden container
		$("#dialog").load('add_country.xhtml',function(){
			//When the loading finished convert the container into a dialog box and show it
			$("#dialog").dialog({
				title: "Add Country",
				autoOpen: false,
	            resizable: false,
	            modal: true,
	            fluid: true,
	            width: 'auto'}).dialog('open');
		})
	})
	
	
	//Get every delete button
	$('.deletecountrybutton').each(function(){
		//Extract the original onclick action
		var originalClick=$(this).get(0).onclick;
		//Remove original onclick
		$(this).attr("onclick","");
		//Add the new onclick action to the button
		$(this).on('click', function() {
			//Add the confirmation text
			$( "#dialog" ).html("Would you like to delete "+$(this).attr('accesskey')+"?");
			//Convert to a dialog. add the buttons and Open up
			$( "#dialog" ).dialog({
				  dialogClass: "no-close",
				  title: "Delete Country",
				  buttons: [
				    {
				      text: "Delete",
				      click: function() {
				    	//Run original click
				    	originalClick();
				        $( this ).dialog( "close" );
				      }
				    },
				    {
				       text: "Cancel",
					   click: function() {
					     $( this ).dialog( "close" );
					     return false;
					   }
				    }
				  ]
				});

			return false;
		});
		
	});
	
	
	
	//Add listener to the dialog close event
	$("body").on("dialogclose", "#dialog",function() {
		 //Reset the dialog
	     $( this ).dialog( "destroy" );
	} );
});

//Function used to check the jsf ajax response after from the form is sent
function checkResponse(data){
	console.log(data.status);
	//Check if the ajax was successful
	if (data.status === 'success') {
		//Check if there is an error message
		if($('.ajaxResponseMessage').html().includes("success")){
			//If there is none then reload
			document.location.reload();
		}
	}
}