export type Tool = {
    id: number
    name: string
    code: string
    warehouse: string
    toolType: string
    deleted: boolean
}

export type ToolHistory = {
    orderStageName: string
    orderStageId: number
    orderStageStartDate: string
    orderStageEndDate: string
    foremanName: string
    foremanId: number
}
