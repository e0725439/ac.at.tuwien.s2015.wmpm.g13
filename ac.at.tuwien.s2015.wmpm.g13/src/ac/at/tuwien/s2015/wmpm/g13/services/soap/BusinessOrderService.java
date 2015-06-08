package ac.at.tuwien.s2015.wmpm.g13.services.soap;

import ac.at.tuwien.s2015.wmpm.g13.model.BusinessOrder;

import javax.jws.WebService;

@WebService
public interface BusinessOrderService {

    public BusinessOrder order(BusinessOrder order);

}
