export interface ApiErrorInfo {
    code: string;
    message: string;
    details?: string[];
}

export interface ApiResponse<T> {
    success: boolean;
    timestamp: string;
    data?: T;
    error?: ApiErrorInfo;
}
