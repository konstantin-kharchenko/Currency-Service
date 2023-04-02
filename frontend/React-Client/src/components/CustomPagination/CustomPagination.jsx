import React from 'react';

const CustomPagination = ({data, newPage, isLoad}) => {
    let paginationItems = null;
    if (isLoad) {
        const min = data.number - 5 < 0 ? 0 : data.numbe - 5;
        const max = data.number + 5 > data.totalPages ? data.totalPages : data.number + 5;
        const numbers = Array.from(Array(max).keys()).slice(min);
        paginationItems = numbers.map((element, index) =>
            <li key={index} className={data.number === element ? 'page-item disabled me-2' : 'page-item me-2'}>
                <button key={index} className={data.number === element ? 'page-link text-dark bg-warning border-dark rounded-2' : 'page-link text-white bg-dark border-dark rounded-2'} onClick={() => {
                    newPage(element);
                }}>{element + 1}</button>
            </li>
        )
    }

    return (
        <nav>
            <ul className="pagination justify-content-center">
                <li className={data.number !== 0 ? "page-item me-2" : "page-item disabled me-2"}>
                    <button className={data.number !== 0 ? "page-link bg-dark text-warning border-dark rounded-2" : "page-link bg-dark border-dark rounded-2"} onClick={() => {
                        newPage(data.number - 1);
                    }}>&laquo;</button>
                </li>
                {paginationItems}
                <li className={data.number + 1 === data.totalPages || data.totalElements === 0 ? "page-item disabled me-2" : "page-item me-2"}>
                    <button className= {data.number + 1 === data.totalPages || data.totalElements === 0 ? "page-link bg-dark border-dark rounded-2" : "page-link bg-dark text-warning border-dark rounded-2"} onClick={() => {
                        newPage(data.number + 1);
                    }} tabIndex="-1">&raquo;</button>
                </li>
            </ul>
        </nav>
    );
};

export default CustomPagination;