

function showView(){
        
        // debugger
        // var node = document.body.querySelector('#viewer-div');
        // var viewer = new WDSV(node, { id:1 });
        // viewer.On("Error", (error) => alert(error.toString()));
}
function displayAllFiles(allFiles,containerID, dateID, name){
    let fileContainer = document.createElement("div");
    fileContainer.className = "fileContainer";
    let date = undefined;
    for(let i = -1; i < allFiles.length; i++) {
        let fileContainerRow = document.createElement("div");
        fileContainerRow.className = "fileContainerRow";
        let fileImage = document.createElement("div");
        fileImage.className = "fileImage";
        let fileContent = document.createElement("div");
        fileContent.className = "fileContent";
        let dateCreated = document.createElement("div");
        dateCreated.className = "dateCreated";
        let author = document.createElement("div");
        author.className = "fileAuthor";
       
        

        if(i > -1){

            let downloadFile = document.createElement("button");
            downloadFile.className = "downloadButton";

            fileImage.innerHTML = '<svg aria-label="File" class="octicon octicon-file text-gray-light" height="16" viewBox="0 0 16 16" version="1.1" width="16" role="img"><path fill-rule="evenodd" d="M3.75 1.5a.25.25 0 00-.25.25v11.5c0 .138.112.25.25.25h8.5a.25.25 0 00.25-.25V6H9.75A1.75 1.75 0 018 4.25V1.5H3.75zm5.75.56v2.19c0 .138.112.25.25.25h2.19L9.5 2.06zM2 1.75C2 .784 2.784 0 3.75 0h5.086c.464 0 .909.184 1.237.513l3.414 3.414c.329.328.513.773.513 1.237v8.086A1.75 1.75 0 0112.25 15h-8.5A1.75 1.75 0 012 13.25V1.75z"></path></svg>'
            fileContent.innerHTML = allFiles[i]["name"];
            dateCreated.innerHTML = allFiles[i]["created"];
            author.innerHTML = allFiles[i]["author"];
            let downloadLink = allFiles[i]["location"];
            downloadFile.innerHTML = '<a href="' + downloadLink + '" target="_blank" download><svg aria-label="File" class="octicon octicon-file text-gray-light" height="16" viewBox="0 0 490 490" version="1.1" width="16" role="img">' +
            '<path id="path2" d="M411.55,100.9l-94.7-94.7c-4.2-4.2-9.4-6.2-14.6-6.2H92.15c-11.4,0-20.8,9.4-20.8,20.8v330.8c0,11.4,9.4,20.8,20.8,20.8   h132.1V421l-16.6-15.2c-8.3-7.3-21.8-7.3-29.1,1s-7.3,21.8,1,29.1l52,47.9c3.1,3.1,14.6,10.2,29.1,0l52-47.9   c8.3-8.3,8.3-20.8,1-29.1c-8.3-8.3-20.8-8.3-29.1-1l-18.7,17.2v-50.5h132.1c11.4,0,19.8-9.4,19.8-19.8V115.5   C417.85,110.3,415.75,105.1,411.55,100.9z M324.15,70.4l39.3,38.9h-39.3V70.4z M265.95,331.9v-130c0-11.4-9.4-20.8-20.8-20.8   c-11.4,0-20.8,9.4-20.8,20.8v130h-111.3V41.6h169.6v86.3c0,11.4,9.4,20.8,20.8,20.8h74.9v183.1h-112.4V331.9z"/>'
             + '</svg></a>';
            


            let fileDate = Date.parse(allFiles[i]["created"]);
            if(date == undefined){
                date = fileDate; 
            }else if(date < fileDate){
                date = fileDate;
            }

            dateCreated.appendChild(downloadFile);
        }else{
            fileImage.innerHTML = '<button class ="backButton"> <svg aria-label="File" class="octicon octicon-file text-gray-light" height="16" viewBox="0 0 30 30" version="1.1" width="16" role="img">' +
            '<path id="path2"  d="M26.105,21.891c-0.229,0-0.439-0.131-0.529-0.346l0,0c-0.066-0.156-1.716-3.857-7.885-4.59   c-1.285-0.156-2.824-0.236-4.693-0.25v4.613c0,0.213-0.115,0.406-0.304,0.508c-0.188,0.098-0.413,0.084-0.588-0.033L0.254,13.815   C0.094,13.708,0,13.528,0,13.339c0-0.191,0.094-0.365,0.254-0.477l11.857-7.979c0.175-0.121,0.398-0.129,0.588-0.029   c0.19,0.102,0.303,0.295,0.303,0.502v4.293c2.578,0.336,13.674,2.33,13.674,11.674c0,0.271-0.191,0.508-0.459,0.562   C26.18,21.891,26.141,21.891,26.105,21.891z" />'
             + '</svg> </button>'
            fileContent.innerHTML = "<strong>File Name</strong>"
           // fileContent.className += "backButton"
            dateCreated.innerHTML = "<strong>Date Modified</strong>"; 
          //  fileContent.innerHTML = "";
            author.innerHTML = "<strong>Author</strong>";
        }

        fileContainerRow.appendChild(fileImage);
        fileContainerRow.appendChild(fileContent);
        fileContainerRow.appendChild(author);
        fileContainerRow.appendChild(dateCreated);
       
        fileContainer.appendChild(fileContainerRow);
        

        
    }

    if(date != undefined){
        date = new Date(date).toLocaleDateString('en-US', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
          })
          $('#' + dateID).html(date + '<button name = "' + name + '" class = "downloadButton zip" value = "'  + containerID + '"> <svg aria-label="File" class="octicon octicon-file text-gray-light" height="16" viewBox="0 0 490 490" version="1.1" width="16" role="img">' +
          '<path id="path2" d="M411.55,100.9l-94.7-94.7c-4.2-4.2-9.4-6.2-14.6-6.2H92.15c-11.4,0-20.8,9.4-20.8,20.8v330.8c0,11.4,9.4,20.8,20.8,20.8   h132.1V421l-16.6-15.2c-8.3-7.3-21.8-7.3-29.1,1s-7.3,21.8,1,29.1l52,47.9c3.1,3.1,14.6,10.2,29.1,0l52-47.9   c8.3-8.3,8.3-20.8,1-29.1c-8.3-8.3-20.8-8.3-29.1-1l-18.7,17.2v-50.5h132.1c11.4,0,19.8-9.4,19.8-19.8V115.5   C417.85,110.3,415.75,105.1,411.55,100.9z M324.15,70.4l39.3,38.9h-39.3V70.4z M265.95,331.9v-130c0-11.4-9.4-20.8-20.8-20.8   c-11.4,0-20.8,9.4-20.8,20.8v130h-111.3V41.6h169.6v86.3c0,11.4,9.4,20.8,20.8,20.8h74.9v183.1h-112.4V331.9z"/>'
           + '</svg></button>');
    }else{
        date = "Folder Empty"
    }

    $("#" + containerID).hide();
    document.getElementById(containerID).appendChild(fileContainer);
   
   }
   function processSimulations(modelSimulations){
        let fileContainer = document.createElement("div");
        fileContainer.className = "fileContainer";
        let date = undefined;
        let keys = Object.keys(modelSimulations);
        



        let fileContainerRow = document.createElement("div");
        fileContainerRow.className = "fileContainerRow";
        let fileImage = document.createElement("div");
        fileImage.className = "fileImage";
        let fileContent = document.createElement("div");
        fileContent.className = "fileContent";
        let dateCreated = document.createElement("div");
        dateCreated.className = "dateCreated";
        let author = document.createElement("div");
        author.className = "fileAuthor";
        fileImage.innerHTML = '<button class ="backButton"> <svg aria-label="File" class="octicon octicon-file text-gray-light" height="16" viewBox="0 0 30 30" version="1.1" width="16" role="img">' +
                                '<path id="path2"  d="M26.105,21.891c-0.229,0-0.439-0.131-0.529-0.346l0,0c-0.066-0.156-1.716-3.857-7.885-4.59   c-1.285-0.156-2.824-0.236-4.693-0.25v4.613c0,0.213-0.115,0.406-0.304,0.508c-0.188,0.098-0.413,0.084-0.588-0.033L0.254,13.815   C0.094,13.708,0,13.528,0,13.339c0-0.191,0.094-0.365,0.254-0.477l11.857-7.979c0.175-0.121,0.398-0.129,0.588-0.029   c0.19,0.102,0.303,0.295,0.303,0.502v4.293c2.578,0.336,13.674,2.33,13.674,11.674c0,0.271-0.191,0.508-0.459,0.562   C26.18,21.891,26.141,21.891,26.105,21.891z" />'
                                + '</svg> </button>'
        fileContent.innerHTML = "<strong>Simulation</strong>"
        dateCreated.innerHTML = "<strong>Click to Run</strong>"; 
          
      //  author.innerHTML = "<strong>Author</strong>";
        fileContainerRow.appendChild(fileImage);
        fileContainerRow.appendChild(fileContent);
        fileContainerRow.appendChild(author);
        fileContainerRow.appendChild(dateCreated);
       
        fileContainer.appendChild(fileContainerRow);

        for(key of keys){

            let fileContainerRow = document.createElement("div");
            fileContainerRow.className = "fileContainerRow";
            let fileImage = document.createElement("div");
            fileImage.className = "fileImage";
            let fileContent = document.createElement("div");
            fileContent.className = "fileContent";
            let dateCreated = document.createElement("div");
            dateCreated.className = "dateCreated";
            let author = document.createElement("div");
            author.className = "fileAuthor";
            let runFile = document.createElement("button");
            runFile.className = "downloadButton";
            fileImage.innerHTML = '<svg aria-label="File" class="octicon octicon-file text-gray-light" height="16" viewBox="0 0 16 16" version="1.1" width="16" role="img"><path fill-rule="evenodd" d="M3.75 1.5a.25.25 0 00-.25.25v11.5c0 .138.112.25.25.25h8.5a.25.25 0 00.25-.25V6H9.75A1.75 1.75 0 018 4.25V1.5H3.75zm5.75.56v2.19c0 .138.112.25.25.25h2.19L9.5 2.06zM2 1.75C2 .784 2.784 0 3.75 0h5.086c.464 0 .909.184 1.237.513l3.414 3.414c.329.328.513.773.513 1.237v8.086A1.75 1.75 0 0112.25 15h-8.5A1.75 1.75 0 012 13.25V1.75z"></path></svg>'
            fileContent.innerHTML = "Simulation " +  modelSimulations[key];
            runFile.innerHTML = '<a class = "runSimulation" name = "' + modelSimulations[key] + '" target="_blank" download><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="Layer_1" x="0px" y="0px" viewBox="0 0 512 512" style="enable-background:new 0 0 512 512;" xml:space="preserve"><g><g><path d="M256,0C114.833,0,0,114.844,0,256s114.833,256,256,256s256-114.844,256-256S397.167,0,256,0z M357.771,264.969    l-149.333,96c-1.75,1.135-3.771,1.698-5.771,1.698c-1.75,0-3.521-0.438-5.104-1.302C194.125,359.49,192,355.906,192,352V160    c0-3.906,2.125-7.49,5.563-9.365c3.375-1.854,7.604-1.74,10.875,0.396l149.333,96c3.042,1.958,4.896,5.344,4.896,8.969    S360.813,263.01,357.771,264.969z"/></g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg></a>';
            
            dateCreated.appendChild(runFile);
            fileContainerRow.appendChild(fileImage);
            fileContainerRow.appendChild(fileContent);
            fileContainerRow.appendChild(author);
            fileContainerRow.appendChild(dateCreated);
            fileContainer.appendChild(fileContainerRow);

        }
        
    $("#simulationsHolder").hide();
    document.getElementById("simulationsHolder").appendChild(fileContainer);
   
   }
   function base64ToArrayBuffer(base64) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
}

$(function(){
    $(document).ready(function() {
        var data = {
            id: Id,
        };
        $.post("/zip" + type, $.param(data), function(d) {
            $(".zip").show();
        });
    });

    // $(".runSimulation").click(function (event){
    //     var name = event.currentTarget.getAttribute('name');
    //     console.log(name + "hello");
       
    // });





   
    $(".zip").click(function (event){
        event.stopPropagation();
        var name = event.currentTarget.getAttribute('name');
        //var modelID = 70;
        var data = {
            Name: name,
        };
        $.post("/zip/files", $.param(data), function(d) {
            saveByteArray(d);
        });
    });

    $('#simulationsSelector').click(function (event) {
        $( '#simulationsHolder' ).show();
        $('#iframe').hide();
        $( '#allFilesHolder' ).hide();
        $( '#sourceFilesHolder' ).hide();
        $( '#resultFilesHolder' ).hide();
        $( '#convertedFilesHolder' ).hide();
        $( '#fileTypeSelector' ).hide();
        
    });

    $('#allFilesSelector').click(function (event) {
        $( '#allFilesHolder' ).show();
        $( '#sourceFilesHolder' ).hide();
        $( '#resultFilesHolder' ).hide();
        $( '#convertedFilesHolder' ).hide();
        $( '#fileTypeSelector' ).hide();
        $( '#simulationsHolder' ).hide();
        $('#iframe').hide();
        
    });

    $('#sourceSelector').click(function () {
        $( '#allFilesHolder' ).hide();
        $( '#sourceFilesHolder' ).show();
        $( '#resultFilesHolder' ).hide();
        $( '#convertedFilesHolder' ).hide();
        $( '#fileTypeSelector' ).hide();
        $( '#simulationsHolder' ).hide();
        $('#iframe').hide();
    });

    $('#convertedSelector').click(function () {
        $( '#allFilesHolder' ).hide();
        $( '#sourceFilesHolder' ).hide();
        $( '#resultFilesHolder' ).show();
        $( '#convertedFilesHolder' ).hide();
        $( '#fileTypeSelector' ).hide();
        $( '#simulationsHolder' ).hide();
        $('#iframe').hide();
    });

    $('#resultSelector').click(function () {
        $( '#allFilesHolder' ).hide();
        $( '#sourceFilesHolder' ).hide();
        $( '#resultFilesHolder' ).hide();
        $( '#convertedFilesHolder' ).show();
        $( '#fileTypeSelector' ).hide();
        $( '#simulationsHolder' ).hide();
        $('#iframe').hide();
    });

    $('.backButton').click(function () {
        $( '#simulationsHolder' ).hide();
        $( '#allFilesHolder' ).hide();
        $( '#sourceFilesHolder' ).hide();
        $( '#resultFilesHolder' ).hide();
        $( '#convertedFilesHolder' ).hide();
        $( '#fileTypeSelector' ).show();
        $('#iframe').hide(); 
    });  
});

function saveByteArray(byte) {

    const contentType = 'application/zip';
    const b64Data = byte;
    const blob = b64toBlob(b64Data, contentType);
    //saveAs(blob, "data");
    var a = document.createElement("a");
    document.body.appendChild(a);
    a.style = "display: none";
    a.href = URL.createObjectURL(blob);
    a.download = "data.Zip";
    a.click();
    document.body.removeChild(a);

};

const b64toBlob = (b64Data, contentType='', sliceSize=512) => {
    const byteCharacters = atob(b64Data);
    const byteArrays = [];
  
    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      const slice = byteCharacters.slice(offset, offset + sliceSize);
  
      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }
  
    const blob = new Blob(byteArrays, {type: contentType});
    return blob;
}