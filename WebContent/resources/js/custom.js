$(document).ready(function() {
	// Turn every table into sortable table
	$('table').DataTable();
	
	
	// Add add region button listener
	$('body').on('click', '#addregionbutton', function() {
		//load the dialog content and open it
		openDialog("Add Region","add_region.xhtml");
	})
	// Add add city button listener
	$('body').on('click', '#addcitybutton', function() {
		//load the dialog content and open it
		openDialog("Add City","add_city.xhtml");
	})
	
	// Add add country button listener
	$('body').on('click', '#addcountrybutton', function() {
		//load the dialog content and open it
		openDialog("Add Country","add_country.xhtml");
	})

	// Add add country button listener
	$('body').on('click', '.updatecountrybutton', function() {
		//load the dialog content and open it
		openDialog("Update Country","update_country.xhtml?code="+$(this).attr("accesskey"));
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
	     $( this ).html("");
	} );
});

//Function used to load the dialog content and open it
function openDialog(title,page){
	//Load the content of add_country.xhtml into a hidden container
	$("#dialog").load(page,function(){
		//When the loading finished convert the container into a dialog box and show it
		$("#dialog").dialog({
			title: title,
			autoOpen: false,
            resizable: false,
            modal: true,
            fluid: true,
            width: 'auto'}).dialog('open');
	})
}

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