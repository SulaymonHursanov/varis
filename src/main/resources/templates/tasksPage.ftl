<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<ul>
<#list tasks as task>
    <li>title ${task.title}</li>
    <li>definition ${task.definition}</li>
    <li>state ${task.state}</li>
    <#else >
</#list>
</ul>

<br>
<br>
<form method="post" action="/tasks/addTask">
    <input name="title" type="text" placeholder="title of task">
    <input name="definition" type="text" placeholder="definition of task">
    <select name="state">

        <option value="OPENED">Opened</option>
        <option value="INPROCCESS">In process</option>
        <option value="CLOSED">Closed</option>

    </select>
    <input type="submit">
</form>
</body>
</html>