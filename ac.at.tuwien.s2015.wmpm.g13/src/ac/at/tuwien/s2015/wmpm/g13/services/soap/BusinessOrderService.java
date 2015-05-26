package ac.at.tuwien.s2015.wmpm.g13.services.soap;

import javax.jws.WebService;

import ac.at.tuwien.s2015.wmpm.g13.model.BusinessOrder;

@WebService
public interface BusinessOrderService {

    public BusinessOrder order(BusinessOrder order);

}
