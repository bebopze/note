local template = require "resty.template"

content = {
    message = "Hello, World!",
    names = { "james", "fox", "tony" }
}
template.render("demo.html", content)  