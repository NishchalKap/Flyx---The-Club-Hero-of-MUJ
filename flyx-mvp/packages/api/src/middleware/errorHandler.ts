import { Request, Response, NextFunction } from 'express';

export const errorHandler = (err: Error, req: Request, res: Response, next: NextFunction) => {
  console.error(err.stack); // Log the error for debugging

  // Default to a 500 server error
  let statusCode = 500;
  let message = 'An unexpected error occurred';

  // Can add custom error types here
  // if (err instanceof CustomError) {
  //   statusCode = err.statusCode;
  //   message = err.message;
  // }

  res.status(statusCode).json({
    success: false,
    message,
  });
};
