package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.AddressInputDTO;
import com.project.pescueshop.model.entity.Address;
import com.project.pescueshop.model.entity.Role;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.dao.UserDAO;
import com.project.pescueshop.util.constant.EnumResponseCode;
import com.project.pescueshop.util.constant.EnumStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService extends BaseService {
    private final UserDAO userDAO;
    private final RoleService roleService;

    public User findByEmail(String email){
        return userDAO.findUserByEmail(email);
    }

    public User addUser(User user){
        List<Role> userRoles = roleService.getDefaultUserRole();
        user.setUserRoles(userRoles);
        userDAO.saveAndFlushUser(user);
        return user;
    }

    public User getAdminUser() throws FriendlyException {
        User admin = findByEmail("admin");

        if (admin == null)
            throw new FriendlyException(EnumResponseCode.NOT_LOGGED_IN);

        return admin;
    }

    public Address addUserAddress(User user, AddressInputDTO dto){
        Address address = Address.builder()
                .userId(user.getUserId())
                .districtName(dto.getDistrictName())
                .cityName(dto.getCityName())
                .wardName(dto.getWardName())
                .streetName(dto.getStreetName())
                .status(EnumStatus.ACTIVE.getValue())
                .build();

        userDAO.saveAndFlushAddress(address);
        return address;
    }

    public List<Address> getAddressListByUser(String userId){
        return userDAO.findAddressByUserId(userId);
    }

    public void deleteUserAddress(String addressId) throws FriendlyException {
        userDAO.deleteAddress(addressId);
    }

    public void addMemberPoint(User user, long point) {
        user.setMemberPoint(user.getMemberPoint() + point);
        userDAO.saveAndFlushUser(user);
    }
}
