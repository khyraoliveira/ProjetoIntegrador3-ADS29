var menuItem = document.querySelectorAll('.item-menu')

function selectlink(){
    menuItem.forEach((item)=>
        item.classList.remove('ativo')
    );
    this.classList.add('ativo')
}

menuItem.forEach((item)=>
    item.addEventListener('click', selectlink)
)

var btnExp = document.querySelector('#btn-exp')
var menuside = document.querySelector('.navbar')

btnExp.addEventListener('click', function(){
    menuside.classList.toggle('expandir')
})