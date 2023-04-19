import { Filter } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { Event } from '../../types/model/Event'
import { getFilteredEvents } from '../../api/event.api'
import { useFormik } from 'formik'

const Events = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryEvents = useQuery<Array<Event>, AxiosError>(['events', filterParams], async () =>
        getFilteredEvents({ queryParams: filterParams }),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,
            // validationSchema={{}}
            onSubmit: () => {
                setFilterStructure(setNewFilterValues(filter.formik.values, filterStructure))
                setFilterParams(getFilterParams(filterStructure))
            },
            onReset: () => filter.formik.setValues(initialValues),
        }),
        inputs: inputs,
    }

    return (
        <FatTable
            query={queryEvents}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'eventDate'}
            onClickRow={(e, row) => {
                if (row.eventType === 'T') {
                    navigation(`/toolevent/${row.id}`)
                } else {
                    navigation(`/elementevent/${row.id}`)
                }
            }}
            pageHeader="Lista usterek"
        />
    )
}

export default Events
