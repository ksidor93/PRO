import { Outlet } from 'react-router-dom'
import Header from '../components/headers/Header'
import LoginPage from '../pages/LoginPage'
import { Role } from '../types/roleEnum'
import Error from '../components/error/Error'
import Orders from '../pages/orders'
import OrderDetails from '../pages/orders/OrderDetails'
import ToolTypes from '../pages/toolTypes'
import ToolTypeDetails from '../pages/toolTypes/ToolTypeDetails'
import WarehouseDetails from '../pages/warehouses/WarehouseDetails'
import Warehouses from '../pages/warehouses'
import Companies from '../pages/company'
import CompanyDetails from '../pages/company/CompanyDetails'
import Tools from '../pages/tools'
import ToolsFromWarehouse from '../pages/tools/ToolsFromWarehouse'
import ToolDetails from '../pages/tools/ToolDetails'
import Elements from '../pages/elements'
import ElementDetails from '../pages/elements/ElementDetails'
<<<<<<< HEAD
import DialogGlobal from '../components/dialogGlobal/DialogGlobal'
import { createContext } from 'react'
import { DialogGlobalProvider } from '../providers/DialogGlobalProvider'
=======
import Employees from '../pages/employees'
import EmpDetails from '../pages/employees/employeesDetails/EmpDetails'
import Clients from '../pages/clients'
import ClientDetails from '../pages/clients/ClientDetails'
>>>>>>> b0c32a396fbae5a67659d71a248f20d6bf2a014a

export type PageProps = {
    name: string
    path: string
    allowedRoles?: Array<Role>
    children?: Array<PageProps>
    component?: JSX.Element
    inNav: boolean
}

const Root = () => {
    return (
        <>
            <DialogGlobalProvider>
                <Header />
                <Outlet />
            </DialogGlobalProvider>
        </>
    )
}

export const pageList: Array<PageProps> = [
    {
        inNav: false,
        name: 'Login',
        path: '/login',
        allowedRoles: [Role['*']],
        component: <LoginPage />,
    },
    {
        inNav: false,
        name: '',
        path: '/',
        allowedRoles: [Role['*']],
        component: <Root />,
        children: [
            {
                inNav: true,
                name: 'Lista Firm',
                path: '/companies',
                allowedRoles: [Role.CLOUD_ADMIN],
                component: <Companies />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista firm',
                        path: '/companies',
                    },
                    {
                        inNav: true,
                        name: 'Dodaj firmę',
                        path: '/companies/new',
                    },
                ],
            },
            {
                inNav: false,
                name: '',
                path: '/companies/:id',
                allowedRoles: [Role.CLOUD_ADMIN],
                component: <CompanyDetails />,
            },
            {
                inNav: true,
                name: 'Typy narzędzi',
                path: '/tooltypes',
                allowedRoles: [Role.WAREHOUSE_MANAGER, Role['*']],
                component: <ToolTypes />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista typów narzędzi',
                        path: '/tooltypes',
                    },
                    {
                        inNav: true,
                        name: 'Dodaj typ narzędzi',
                        path: '/tooltypes/new',
                    },
                ],
            },
            {
                inNav: false,
                name: '',
                path: '/tooltypes/:id',
                allowedRoles: [Role.WAREHOUSE_MANAGER, Role['*']],
                component: <ToolTypeDetails />,
            },
            {
                inNav: true,
                name: 'Narzędzia',
                path: '/tools',
                allowedRoles: [Role.MANAGER, Role.SPECIALIST, Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                component: <Tools />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista narzędzi',
                        path: '/tools',
                    },
                    {
                        inNav: true,
                        name: 'Dodaj narzędzie',
                        path: '/tools/new',
                    },
                ],
            },
            {
                inNav: false,
                name: '',
                path: '/tools-warehouse/:warehouseId',
                allowedRoles: [Role.MANAGER, Role.SPECIALIST, Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                component: <ToolsFromWarehouse />,
            },
            {
                inNav: false,
                name: '',
                path: '/tools/:id',
                allowedRoles: [Role.MANAGER, Role.SPECIALIST, Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                component: <ToolDetails />,
            },
            {
                inNav: true,
                name: 'Zlecenia',
                path: '/orders',
                allowedRoles: [
                    Role.FITTER,
                    Role.SPECIALIST,
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.FOREMAN,
                    Role['*'],
                ],
                component: <Orders />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista zleceń',
                        path: '/orders',
                    },
                    {
                        inNav: true,
                        name: 'Dodaj zlecenie',
                        path: '/orders/new',
                    },
                ],
            },
            {
                inNav: false,
                name: '',
                path: '/orders/:id',
                allowedRoles: [
                    Role.FITTER,
                    Role.SPECIALIST,
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.FOREMAN,
                    Role['*'],
                ],
                component: <OrderDetails />,
            },
            {
                inNav: true,
                name: 'Klienci',
                path: '/clients',
                allowedRoles: [Role.MANAGER, Role.SALES_REPRESENTATIVE],
                component: <Clients />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista klientów',
                        path: '/clients',
                    },
                    {
                        inNav: true,
                        name: 'Dodaj klienta',
                        path: '/clients/new',
                    },
                ],
            },
            {
                inNav: false,
                name: '',
                path: '/clients/:id',
                allowedRoles: [Role.MANAGER, Role.SALES_REPRESENTATIVE],
                component: <ClientDetails />,
            },
            {
                inNav: true,
                name: 'Pracownicy',
                path: '/employees',
                allowedRoles: [
                    Role.FITTER,
                    Role.SPECIALIST,
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.FOREMAN,
                    Role.WAREHOUSE_MAN,
                    Role.WAREHOUSE_MANAGER,
                    Role['*'],
                ],
                component: <Employees />,
            },
            {
                inNav: false,
                name: '',
                path: '/employees/:id',
                allowedRoles: [Role.MANAGER, Role['*']],
                component: <EmpDetails />,
            },
            {
                inNav: true,
                name: 'Magazyny',
                path: '/warehouses',
                allowedRoles: [
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.SPECIALIST,
                    Role.WAREHOUSE_MAN,
                    Role.WAREHOUSE_MANAGER,
                    Role.FITTER,
                    Role.FOREMAN,
                    Role['*'],
                ],
                component: <Warehouses />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista magazynów',
                        path: '/warehouses',
                    },
                    {
                        inNav: true,
                        name: 'Dodaj magazyn',
                        path: '/warehouses/new',
                    },
                ],
            },
            {
                inNav: false,
                name: '',
                path: '/warehouses/:id',
                allowedRoles: [
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.SPECIALIST,
                    Role.WAREHOUSE_MAN,
                    Role.WAREHOUSE_MANAGER,
                    Role.FITTER,
                    Role.FOREMAN,
                    Role['*'],
                ],
                component: <WarehouseDetails />,
            },
            {
                inNav: true,
                name: 'Elementy',
                path: '/elements',
                allowedRoles: [Role.MANAGER, Role.SPECIALIST, Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                component: <Elements />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista elementów',
                        path: '/elements',
                    },
                    {
                        inNav: true,
                        name: 'Dodaj element',
                        path: '/elements/new',
                    },
                ],
            },
            {
                inNav: false,
                name: '',
                path: '/elements/:id',
                allowedRoles: [Role.MANAGER, Role.SPECIALIST, Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                component: <ElementDetails />,
            },
        ],
    },
    {
        inNav: false,
        name: 'Error',
        path: '*',
        allowedRoles: [Role['*']],
        component: (
            <Error code={404} message={'Nie znaleziono strony'} description={'Zgubiłeś się ? Wróć na stronę główną!'} />
        ),
    },
]
