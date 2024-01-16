import {Outlet} from "react-router-dom";

function Header() {
    return (<>
        <div className="container" id="header">
            <p>Лабораторная работа #4</p>
            <p>Выполнил Краков Кирилл P32131</p>
            <p>Вариант 2838</p>
        </div>
        <Outlet/>
    </>);
}

export default Header;
