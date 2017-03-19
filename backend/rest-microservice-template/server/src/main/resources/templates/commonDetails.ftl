<style>
    <#include "styles.css"/>
</style>

<div class="application-details-wrapper">
<#if applicationBean.clientData??>
    <#assign clientData = applicationBean.clientData>

    <h1>Client details</h1>
    <div>First name: ${clientData.firstName!}</div>
    <div>Last name: ${clientData.lastName!}</div>
    <div>Last name 2: ${clientData.lastName2!}</div>
    <div>Personal id: ${clientData.personalId!}</div>
    <div>Birth date: ${clientData.dateOfBirth!}</div>
    <div>Company: ${clientData.company!}</div>
    <div>Email: ${clientData.email!}</div>
    <div>Phone: ${clientData.phone!}</div>
    <div>Phone 2: ${clientData.phone2!}</div>
    <div>Bank account: ${clientData.bankAccount!}</div>
    <div>
        Promotions: <#if clientData.promotions??>${clientData.promotions?string('true', 'false')}</#if>
    </div>
</#if>

<#if applicationBean.address??>
    <#assign address = applicationBean.address>
    <h2>Address</h2>
    <div>Country: ${address.country!}</div>
    <div>Province: ${address.province!}</div>
    <div>City: ${address.city!}</div>
    <div>Street: ${address.street!}</div>
    <div>Number: ${address.number!}</div>
    <div>Letter: ${address.letter!}</div>
    <div>Flat: ${address.flat!}</div>
    <div>Postal code: ${address.postalCode!}</div>
</#if>

<#if applicationBean.order??>
    <#assign order = applicationBean.order>
    <h1>Order</h1>
    <div>Order id: ${order.id!}</div>
    <div>Client ip: ${order.clientIP!}</div>
    <div>Country: ${order.country!}</div>
    <div>Amount: ${order.amount!}</div>
    <div>Purchased total amount: ${order.purchasedTotalAmount!}</div>
    <div>Currency: ${order.currency!}</div>
    <div>Created: <#if order.created??>${formatter.format(order.created)}</#if></div>

    <#if order.items?has_content>
        <#list order.items>
            <h2>Order shopping cart items</h2>
            <table border="1" cellspacing="0" class="order-items-table">
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
                <#items as orderItem>
                    <tr>
                        <td>${orderItem.name!}</td>
                        <td>${orderItem.category!}</td>
                        <td>${orderItem.price!}</td>
                        <td>${orderItem.quantity!}</td>
                    </tr>
                </#items>
            </table>
        </#list>
    </#if>

    <#if order.client??>
        <#assign client = order.client>
        <h2>Merchant client</h2>
        <div>First name: ${client.firstName!}</div>
        <div>Last name: ${client.lastName!}</div>
        <div>Last name 2: ${client.lastName2!}</div>
        <div>Personal id: ${client.personalId!}</div>
        <div>Birth date: ${client.dateOfBirth!}</div>
        <div>Company: ${client.company!}</div>
        <div>Email: ${client.email!}</div>
        <div>Phone: ${client.phone!}</div>
        <div>Registration date: ${client.registrationDate!}</div>

        <#list client.orderHistory>
            <div class="history-order-conainer">
                <h3>Merchant client order history</h3>
                <#items as order>

                    <div class="history-order-item">
                        <div>
                            <div>Created <#if order.created??>${formatter.format(order.created)}</#if></div>
                            <div>Amount ${order.amount!}</div>
                            <div>Currency ${order.currency!}</div>
                            <div>State ${order.state!}</div>
                            <div>Country ${order.country!}</div>
                        </div>
                        <div>
                            <#if order.items?has_content>
                                <#list order.items>
                                    <table border="1" cellspacing="0" class="order-items-table">
                                        <tr>
                                            <th>Name</th>
                                            <th>Category</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                        </tr>
                                        <#items as orderItem>
                                            <tr>
                                                <td>${orderItem.name!}</td>
                                                <td>${orderItem.category!}</td>
                                                <td>${orderItem.price!}</td>
                                                <td>${orderItem.quantity!}</td>
                                            </tr>
                                        </#items>
                                    </table>
                                </#list>
                            </#if>
                        </div>
                    </div>

                </#items>
            </div>
        </#list>
    </#if>

    <#if order.shippingAddress??>
        <#assign shippingAddress = order.shippingAddress>
        <h2>Shipping address</h2>
        <div>Country: ${shippingAddress.country!}</div>
        <div>Province: ${shippingAddress.province!}</div>
        <div>City: ${shippingAddress.city!}</div>
        <div>Street: ${shippingAddress.street!}</div>
        <div>Number: ${shippingAddress.number!}</div>
        <div>Letter: ${shippingAddress.letter!}</div>
        <div>Flat: ${shippingAddress.flat!}</div>
        <div>Postal code: ${shippingAddress.postalCode!}</div>
    </#if>

    <#if order.billingAddress??>
        <#assign billingAddress = order.billingAddress>
        <h2>Billing address</h2>
        <div>Country: ${billingAddress.country!}</div>
        <div>Province: ${billingAddress.province!}</div>
        <div>City: ${billingAddress.city!}</div>
        <div>Street: ${billingAddress.street!}</div>
        <div>Number: ${billingAddress.number!}</div>
        <div>Letter: ${billingAddress.letter!}</div>
        <div>Flat: ${billingAddress.flat!}</div>
        <div>Postal code: ${billingAddress.postalCode!}</div>
    </#if>
</#if>

<#if applicationBean.applicationDetails??>
    <#assign applicationDetails = applicationBean.applicationDetails>
    <h2>Application details</h2>
    <div>Monthly income: ${applicationDetails.monthlyIncome!}</div>
    <div>Monthly credit payments: ${applicationDetails.monthlyCreditPayments!}</div>
    <div>Employee status: ${applicationDetails.employmentStatus!}</div>
    <div>Industry: ${applicationDetails.industry!}</div>
    <div>Profession: ${applicationDetails.profession!}</div>
</#if>

<#if applicationBean.endUser??>
    <#include "icDetails.ftl"/>
</#if>
</div>