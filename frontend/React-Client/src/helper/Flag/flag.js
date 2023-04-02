import america from "../../images/Flag_of_America.png";
import europe from "../../images/Flag_of_Europe.png";
import british from "../../images/Flag_of_British.png";
import belarus from "../../images/Flag_of_Belarus.png";

export const getFlag = (flag) =>{

    if (flag === 'USD') {
        return america;
    } else if (flag === 'EUR') {
        return europe;
    } else if (flag === 'GBP') {
        return british;
    }
    else if (flag === 'BYN') {
        return belarus;
    }
}